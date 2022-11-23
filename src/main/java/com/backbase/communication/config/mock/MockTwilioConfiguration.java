package com.backbase.communication.config.mock;

import com.backbase.communication.config.TwilioConfigurationProperties;
import com.twilio.Twilio;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Configuration
@Profile("system-test")
public class MockTwilioConfiguration {

    private final TwilioConfigurationProperties twilioConfigurationProperties;

    private final MockTwilioClientCreator mockTwilioClientCreator;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioConfigurationProperties.getAccountSid(), twilioConfigurationProperties.getAuthToken());
        Twilio.setRestClient(mockTwilioClientCreator.getClient());
    }
}
