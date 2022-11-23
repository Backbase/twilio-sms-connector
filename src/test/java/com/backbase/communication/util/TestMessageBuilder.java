package com.backbase.communication.util;

import com.backbase.communication.event.spec.v1.SmsChannelEvent;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.communication.model.Message;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TestMessageBuilder {
    public Message<com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse> createMessageV1() {
        Message<com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse> message = new Message<>();
        message.setPriority(1);
        message.setTrackingId(UUID.randomUUID());
        message.setTag("tag");
        message.setExpiresAt(ZonedDateTime.now().plus(1, ChronoUnit.HOURS));
        message.setCallbackUrl("callbackUrl");
        message.setDeliveryChannel("sms");
        com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse batchResponse = new com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse();
        Content content = new Content();
        content.setBody("body");
        content.setContentId("content-id");
        content.setTitle("title");
        batchResponse.setContent(List.of(content));
        com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient recipient = new com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient();
        recipient.setContentId("content-id");
        recipient.setFrom("06123456");
        recipient.setRef("ref");
        recipient.setTo(List.of("06654321"));
        batchResponse.setRecipients(List.of(recipient));
        message.setPayload(batchResponse);
        return message;
    }

    public Message<SmsChannelEvent> createMessageV2() {
        Message<SmsChannelEvent> message = new Message<>();
        message.setPriority(1);
        message.setTrackingId(UUID.randomUUID());
        message.setTag("tag");
        message.setExpiresAt(ZonedDateTime.now().plus(1, ChronoUnit.HOURS));
        message.setCallbackUrl("callbackUrl");
        message.setDeliveryChannel("sms");
        SmsChannelEvent smsChannelEvent = new SmsChannelEvent();
        smsChannelEvent.setMessage("sample sms message");
        smsChannelEvent.setFrom("06123456");
        smsChannelEvent.setTo(Set.of("06654321"));
        message.setPayload(smsChannelEvent);
        return message;
    }
}
