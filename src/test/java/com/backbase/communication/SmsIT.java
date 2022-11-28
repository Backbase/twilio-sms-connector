package com.backbase.communication;

import com.backbase.buildingblocks.commns.model.ProcessingStatus;
import com.backbase.buildingblocks.testutils.TestTokenUtil;
import com.backbase.communication.event.spec.v1.SmsChannelEvent;
import com.backbase.communication.model.Message;
import com.backbase.communication.tracking.TrackingReceiver;
import com.backbase.communication.util.TestMessageBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cloud.stream.test.binder.TestSupportBinderAutoConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Testcontainers
@SpringBootTest(classes = {TwilioSmsConnectorApplication.class})
@ContextConfiguration(classes = TwilioSmsConnectorApplication.class, initializers = {SmsIT.Initializer.class})
@ActiveProfiles({"default", "system-test"})
@EnableAutoConfiguration(exclude = TestSupportBinderAutoConfiguration.class)
@Slf4j
public class SmsIT {
    static DockerComposeContainer dockerCompose = new DockerComposeContainer(new File("src/test/resources/docker-compose-test.yaml"))
            .withExposedService("message-broker", 61616)
            .withExposedService("twiliomock", 4010)
            .withExposedService("communication", 8080)
            .withLogConsumer("message-broker", new Slf4jLogConsumer(log))
            .withLogConsumer("twiliomock", new Slf4jLogConsumer(log))
            .withLogConsumer("communication", new Slf4jLogConsumer(log));
    ;

    private final RestTemplate template = new RestTemplate();
    @Autowired
    TrackingReceiver trackingReceiver;

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            dockerCompose.start();
            TestPropertyValues.of(
                            "spring.activemq.broker-url=tcp://%s:%s"
                                    .formatted(dockerCompose.getServiceHost("message-broker", 61616),
                                            dockerCompose.getServicePort("message-broker", 61616)),
                            "twilio.mockUrl=http://%s:%s"
                                    .formatted(dockerCompose.getServiceHost("twiliomock", 4010),
                                            dockerCompose.getServicePort("twiliomock", 4010)))
                    .applyTo(configurableApplicationContext.getEnvironment());

        }
    }

    @BeforeAll
    public static void envSetup() {
        System.setProperty("TESTCONTAINERS_RYUK_DISABLED", "true");
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
    }

    @BeforeEach
    void init() {
        trackingReceiver.setSmsIT(this);
    }

    @Autowired
    private ObjectMapper objectMapper;

    private List<String> trackingCodes = new ArrayList<>();

    @Test
    void sendSmsV1Test() throws InterruptedException, JsonProcessingException {
        TestMessageBuilder testMessageBuilder = new TestMessageBuilder();
        Message<com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse> message = testMessageBuilder.createMessageV1();

        String host = dockerCompose.getServiceHost("communication", 8080);
        int port = dockerCompose.getServicePort("communication", 8080);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + TestTokenUtil.encode(TestTokenUtil.serviceClaimSet()));

        RequestEntity<String> request = RequestEntity.post("http://%s:%s/service-api/v1/messages".formatted(host, port))
                .headers(headers)
                .body("""
                        {
                          "messages": [
                            %s
                          ]
                        }
                        """.formatted(objectMapper.writeValueAsString(message)));

        ResponseEntity<Void> exchange = template.exchange(request, Void.TYPE);
        Assertions.assertEquals(HttpStatus.ACCEPTED, exchange.getStatusCode());

        await().atMost(2, TimeUnit.SECONDS).until(() -> !trackingCodes.isEmpty());

        assertThat(trackingCodes).isNotEmpty();
    }

    public void trackingReceived(String trackingId, ProcessingStatus status) {
        trackingCodes.add(trackingId);
        log.debug("Tracking received for TrackId:{}, status:{}", trackingId, status);
        assertFalse(Objects.isNull(trackingId) && ProcessingStatus.FAILED.equals(status));
    }

    @Test
    void sendSmsV2Test() throws InterruptedException, JsonProcessingException {
        TestMessageBuilder testMessageBuilder = new TestMessageBuilder();
        Message<SmsChannelEvent> message = testMessageBuilder.createMessageV2();

        String host = dockerCompose.getServiceHost("communication", 8080);
        int port = dockerCompose.getServicePort("communication", 8080);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + TestTokenUtil.encode(TestTokenUtil.serviceClaimSet()));

        RequestEntity<String> request = RequestEntity.post("http://%s:%s/service-api/v1/messages".formatted(host, port))
                .headers(headers)
                .body("""
                        {
                          "messages": [
                            %s
                          ]
                        }
                        """.formatted(objectMapper.writeValueAsString(message)));

        ResponseEntity<Void> exchange = template.exchange(request, Void.TYPE);
        Assertions.assertEquals(HttpStatus.ACCEPTED, exchange.getStatusCode());

        await().atMost(2, TimeUnit.SECONDS).until(() -> !trackingCodes.isEmpty());

        assertThat(trackingCodes).isNotEmpty();
    }
}
