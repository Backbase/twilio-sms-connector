package com.backbase.communication;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import com.backbase.communication.model.SmsV1;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SmsV1Factory {

    public static SmsV1 smsV1() {
        return new SmsV1(new BatchResponse()
                .recipients(List.of(new Recipient()
                        .ref("1")
                        .from("+123456789")
                        .to(List.of("+01456789"))
                        .contentId("1")
                        .data(Map.of("otp", "123456"))))
                .content(List.of(new Content()
                        .contentId("1")
                        .title("OTP")
                        .body("This is your {otp}"))));
    }

    public static SmsV1 emptyRecipientSmsV1() {
        return new SmsV1(new BatchResponse()
                .recipients(Collections.emptyList())
                .content(List.of(new Content()
                        .contentId("1")
                        .title("OTP")
                        .body("This is your {otp}"))));
    }

    public static SmsV1 emptyContentSmsV1() {
        return new SmsV1(new BatchResponse()
                .recipients(List.of(new Recipient()
                        .ref("1")
                        .from("+123456789")
                        .to(List.of("+01456789"))
                        .contentId("1")
                        .data(Map.of("otp", "123456"))))
                .content(Collections.emptyList()));
    }

    public static SmsV1 mismatchedContentIdSmsV1() {
        return new SmsV1(new BatchResponse()
                .recipients(List.of(new Recipient()
                        .ref("1")
                        .from("+123456789")
                        .to(List.of("+01456789"))
                        .contentId("1")
                        .data(Map.of("otp", "123456"))))
                .content(List.of(new Content()
                        .contentId("2")
                        .title("OTP")
                        .body("This is your {otp}"))));
    }

}
