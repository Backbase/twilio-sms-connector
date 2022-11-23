package com.backbase.communication.config.mock;

import com.twilio.http.TwilioRestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("system-test")
class MockTwilioClientCreatorTest {

    static {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
        System.setProperty("twilio.mockUrl", "https://twilio-mock-server");
    }

    @Autowired
    MockTwilioClientCreator mockTwilioClientCreator;

    @Test
    void getClient() {
        TwilioRestClient client = mockTwilioClientCreator.getClient();
        assertThat(client.getHttpClient()).isNotNull();
        assertThat(client.getHttpClient()).isInstanceOf(MockHttpClient.class);
        assertThat(client.getAccountSid()).isNotEmpty();
    }
}