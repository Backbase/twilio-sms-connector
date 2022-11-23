package com.backbase.communication.config;

import com.twilio.Twilio;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Configuration
@Profile("!system-test")
public class TwilioConfiguration {

    private final TwilioConfigurationProperties twilioConfigurationProperties;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(twilioConfigurationProperties.getAccountSid(), twilioConfigurationProperties.getAuthToken());
    }
}
