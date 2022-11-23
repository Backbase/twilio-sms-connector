package com.backbase.communication.mapper;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import com.backbase.communication.model.SmsRequest;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SmsV1MapperTest {

    private final Recipient sampleRecipient = new Recipient();
    private final Content sampleContent = new Content();
    private final List<String> targetList = new ArrayList<String>();
    private final SmsV1Mapper mapper = new SmsV1Mapper();
    private SmsRequest smsRequest_a;
    private SmsRequest smsRequest_b;

    @BeforeEach
    protected void setUp() {
        this.sampleRecipient.setFrom("9782364446");
        this.targetList.add("text:345634567");
        this.targetList.add("sms:452745678");
        this.sampleRecipient.setTo(targetList);
        this.sampleRecipient.setData(Collections.singletonMap("type", "test"));
        this.sampleContent.setTitle("this is a test title ");
        this.sampleContent.setBody("this is ${type} text");
        this.smsRequest_a = SmsRequest.builder()
                .fromNumber(new PhoneNumber("9782364446"))
                .toNumber(new PhoneNumber("345634567"))
                .message("this is test text")
                .build();
        this.smsRequest_b = SmsRequest.builder()
                .fromNumber(new PhoneNumber("9782364446"))
                .toNumber(new PhoneNumber("452745678"))
                .message("this is test text")
                .build();
    }

    @Test
    void testToSmsRequestRecipientContent() {
        List<SmsRequest> testRequestList = mapper.toSmsRequest(this.sampleRecipient, this.sampleContent);

        assertThat(testRequestList.get(0).getFromNumber().toString()).hasToString(smsRequest_a.getFromNumber().toString());
        assertThat(testRequestList.get(0).getToNumber().toString()).hasToString(smsRequest_a.getToNumber().toString());
        assertThat(testRequestList.get(0).getMessage()).isEqualTo(smsRequest_a.getMessage());
        assertThat(testRequestList.get(1).getFromNumber().toString()).hasToString(smsRequest_b.getFromNumber().toString());
        assertThat(testRequestList.get(1).getToNumber().toString()).hasToString(smsRequest_b.getToNumber().toString());
        assertThat(testRequestList.get(1).getMessage()).isEqualTo(smsRequest_b.getMessage());
    }

}
