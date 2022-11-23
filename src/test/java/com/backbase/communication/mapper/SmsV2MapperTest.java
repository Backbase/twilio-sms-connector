package com.backbase.communication.mapper;

import com.backbase.communication.model.SmsRequest;
import com.backbase.communication.model.SmsV2;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class SmsV2MapperTest {

    private final Set<String> targetSet = new HashSet<>();
    private final SmsV2Mapper mapper = new SmsV2Mapper();
    private SmsRequest smsRequest_a;
    private SmsRequest smsRequest_b;

    private SmsV2 smsV2 = new SmsV2();

    @BeforeEach
    protected void setUp() {
        this.smsV2.setFrom("9782364446");
        this.targetSet.add("text:345634567");
        this.targetSet.add("sms:452745678");
        this.smsV2.setTo(targetSet);
        this.smsV2.setMessage("this is text");
        this.smsRequest_a = SmsRequest.builder()
                .fromNumber(new PhoneNumber("9782364446"))
                .toNumber(new PhoneNumber("452745678"))
                .message("this is text")
                .build();
        this.smsRequest_b = SmsRequest.builder()
                .fromNumber(new PhoneNumber("9782364446"))
                .toNumber(new PhoneNumber("345634567"))
                .message("this is text")
                .build();
    }

    @Test
    void testToSmsRequestRecipientContent() {
        List<SmsRequest> testRequestList = mapper.toSmsRequest(this.smsV2);

        assertThat(testRequestList.get(0).getFromNumber().toString()).hasToString(smsRequest_a.getFromNumber().toString());
        assertThat(testRequestList.get(0).getToNumber().toString()).hasToString(smsRequest_a.getToNumber().toString());
        assertThat(testRequestList.get(0).getMessage()).isEqualTo(smsRequest_a.getMessage());
        assertThat(testRequestList.get(1).getFromNumber().toString()).hasToString(smsRequest_b.getFromNumber().toString());
        assertThat(testRequestList.get(1).getToNumber().toString()).hasToString(smsRequest_b.getToNumber().toString());
        assertThat(testRequestList.get(1).getMessage()).isEqualTo(smsRequest_b.getMessage());
    }

}
