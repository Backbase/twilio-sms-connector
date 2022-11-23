package com.backbase.communication.config.mock;

import com.backbase.communication.config.TwilioConfigurationProperties;
import com.twilio.http.TwilioRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("system-test")
public class MockTwilioClientCreator {
    private final MockHttpClient mockHttpClient;
    private final TwilioConfigurationProperties twilioConfigurationProperties;

    public TwilioRestClient getClient() {
        TwilioRestClient.Builder builder = new TwilioRestClient.Builder(twilioConfigurationProperties.getAccountSid(), twilioConfigurationProperties.getAuthToken());
        return builder.httpClient(this.mockHttpClient).build();
    }
}