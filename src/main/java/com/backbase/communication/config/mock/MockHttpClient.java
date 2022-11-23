package com.backbase.communication.config.mock;

import com.twilio.http.NetworkHttpClient;
import com.twilio.http.Request;
import com.twilio.http.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/*
This class is used for system tests in order to rewrite twilio url.
More info:
- https://www.twilio.com/docs/libraries/java/custom-http-clients-java
- https://www.twilio.com/docs/openapi/mock-api-generation-with-twilio-openapi-spec
 */
@Component
@Profile("system-test")
public class MockHttpClient extends NetworkHttpClient {
    @Value("${twilio.mockUrl}")
    private String mockServerUrl;



    @Override
    public Response makeRequest(Request request) {
        String url = request.getUrl().replaceAll("https://.*?.twilio.com", mockServerUrl);
        Request modifiedRequest = new Request(request.getMethod(), url);
        modifiedRequest.getPostParams().putAll(request.getPostParams());
        modifiedRequest.setAuth(request.getUsername(), request.getPassword());
        return super.makeRequest(modifiedRequest);
    }
}
