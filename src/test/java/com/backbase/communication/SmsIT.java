package com.backbase.communication;

import com.backbase.buildingblocks.commns.model.ProcessingStatus;
import com.backbase.buildingblocks.commns.service.MessageSenderService;
import com.backbase.communication.client.TwilioClientImpl;
import com.backbase.communication.communication.CommunicationChannelConsumer;
import com.backbase.communication.communication.CommunicationChannelListener;
import com.backbase.communication.communication.CommunicationChannelQueueService;
import com.backbase.communication.config.MessageChannelProperties;
import com.backbase.communication.config.MessageChannelPropertiesConverter;
import com.backbase.communication.config.TwilioConfigurationProperties;
import com.backbase.communication.config.mock.MockHttpClient;
import com.backbase.communication.config.mock.MockTwilioClientCreator;
import com.backbase.communication.config.mock.MockTwilioConfiguration;
import com.backbase.communication.event.spec.v1.SmsChannelEvent;
import com.backbase.communication.mapper.SmsV1Mapper;
import com.backbase.communication.mapper.SmsV2Mapper;
import com.backbase.communication.model.Message;
import com.backbase.communication.sender.MessageSender;
import com.backbase.communication.sender.MessageSenderProperties;
import com.backbase.communication.service.SmsNotificationService;
import com.backbase.communication.service.SmsServiceV1;
import com.backbase.communication.service.SmsServiceV2;
import com.backbase.communication.service.SmsStrategyFactory;
import com.backbase.communication.tracking.TrackingReceiver;
import com.backbase.communication.util.TestMessageBuilder;
import com.backbase.communication.validator.SmsV1Validator;
import com.backbase.communication.validator.SmsV2Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.TestSupportBinderAutoConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {MessageSender.class, MessageSenderProperties.class, TrackingReceiver.class, MessageChannelProperties.class,
        MessageChannelPropertiesConverter.class, CommunicationChannelConsumer.class, SmsStrategyFactory.class,
        SmsServiceV1.class, SmsServiceV2.class, SmsV1Validator.class, SmsV2Validator.class, SmsNotificationService.class,
        TwilioClientImpl.class, SmsV1Mapper.class, SmsV2Mapper.class, CommunicationChannelListener.class,
        MessageSenderService.class, CommunicationChannelQueueService.class, MockTwilioConfiguration.class, MockTwilioConfiguration.class,
        TwilioConfigurationProperties.class, MockTwilioClientCreator.class, MockHttpClient.class})
@EnableAutoConfiguration(exclude = TestSupportBinderAutoConfiguration.class)

@Slf4j
public class SmsIT {
    static DockerComposeContainer dockerCompose;

    @Autowired
    TrackingReceiver trackingReceiver;

    static {
        System.setProperty("TESTCONTAINERS_RYUK_DISABLED", "true");
        System.setProperty("spring.profiles.active", "system-test");
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
        dockerCompose = new DockerComposeContainer(new File("src/test/resources/docker-compose-test.yaml"))
                .withExposedService("message-broker", 61616)
                .withExposedService("twiliomock", 4010)
                .withLogConsumer("message-broker", new Slf4jLogConsumer(log))
                .withLogConsumer("twiliomock", new Slf4jLogConsumer(log));
        dockerCompose.start();
        System.setProperty("spring.activemq.broker-url", "tcp://localhost:" + dockerCompose.getServicePort("message-broker", 61616));
        System.setProperty("twilio.mockUrl", "http://localhost:" + dockerCompose.getServicePort("twiliomock", 4010));
    }

    @BeforeEach
    void init() {
        trackingReceiver.setSmsIT(this);
    }

    @Autowired
    private MessageSender messageSender;

    private List<String> trackingCodes = new ArrayList<>();

    @Test
    public void sendSmsV1Test() throws InterruptedException {
        TestMessageBuilder testMessageBuilder = new TestMessageBuilder();
        Message<com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse> message = testMessageBuilder.createMessageV1();
        messageSender.sendMessage(message);

        Thread.sleep(5000);

        assertThat(trackingCodes).contains(message.getTrackingId().toString());
    }

    public void trackingReceived(String trackingId, ProcessingStatus status) {
        trackingCodes.add(trackingId);
        log.debug("Tracking received for TrackId:{}, status:{}", trackingId, status);
        assertFalse(Objects.isNull(trackingId) && ProcessingStatus.FAILED.equals(status));
    }

    @Test
    public void sendSmsV2Test() throws InterruptedException {
        TestMessageBuilder testMessageBuilder = new TestMessageBuilder();
        Message<SmsChannelEvent> message = testMessageBuilder.createMessageV2();
        messageSender.sendMessage(message);

        Thread.sleep(5000);

        assertThat(trackingCodes).contains(message.getTrackingId().toString());
    }
}
