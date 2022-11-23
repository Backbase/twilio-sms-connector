package com.backbase.communication.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class SendableTest {

    @Autowired
    private ObjectMapper objectMapper;

    static {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
    }
    @Test
    void testSmsV1Deserialization() throws JsonProcessingException {
        String jsonString = """
                {
                                "recipients": [{
                                    "contentId":"contentId",
                                    "ref":"ref",
                                    "to":["+123456"],
                                    "from": "+987654",
                                    "data":{"type":"test"}
                                    }],
                                "content":[{
                                    "contentId":"contentId",
                                    "body": "body"
                                }]
                            }""";
        Sendable sendable = objectMapper.readValue(jsonString, Sendable.class);
        assertThat(sendable).isInstanceOf(SmsV1.class);
    }

    @Test
    void testSmsV2Deserialization() throws JsonProcessingException {
        String jsonString= """
                {
                                "message": "This is the sms body for john",
                                "to": ["+123456"],
                                "from": "+987654"
                            }""";
        Sendable sendable = objectMapper.readValue(jsonString, Sendable.class);
        assertThat(sendable).isInstanceOf(SmsV2.class);
    }
}