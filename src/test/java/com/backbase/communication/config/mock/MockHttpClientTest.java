package com.backbase.communication.config.mock;

import com.twilio.exception.ApiException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest(properties = {"twilio.mockUrl=http://somemockurl"})
@ActiveProfiles("system-test")
class MockHttpClientTest {

    static {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
    }

    @Autowired
    MockHttpClient mockHttpClient;

    @Test
    void makeRequest() {
        Request request = new Request(HttpMethod.POST, "https://test.twilio.com/api");
        ApiException exception = assertThrows(ApiException.class, () -> mockHttpClient.makeRequest(request));
        assertThat(exception.getCause()).isInstanceOf(UnknownHostException.class);
        assertThat(exception.getMessage()).contains("somemockurl");
    }
}