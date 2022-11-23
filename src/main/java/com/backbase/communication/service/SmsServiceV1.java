package com.backbase.communication.service;

import com.backbase.communication.mapper.SmsV1Mapper;
import com.backbase.communication.model.Error;
import com.backbase.communication.model.Sendable;
import com.backbase.communication.model.SmsV1;
import com.backbase.communication.model.SmsVersionEnum;
import com.backbase.communication.model.Status;
import com.backbase.communication.util.DeliveryCodes;
import com.backbase.communication.validator.SmsV1Validator;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class SmsServiceV1 implements SmsService {
    private final SmsV1Validator smsV1Validator;
    private final SmsNotificationService smsNotificationService;
    private final SmsV1Mapper smsV1Mapper;

    @Override
    public SmsVersionEnum getVersion() {
        return SmsVersionEnum.V1;
    }

    @Override
    public void handleSms(Sendable sendable) {
        SmsV1 smsV1 = (SmsV1) sendable;
        smsV1Validator.validate(smsV1);

        Map<String, com.backbase.outbound.integration.communications.rest.spec.v1.model.Content> contentMap = smsV1.getContent()
                .stream()
                .collect(Collectors.toMap(com.backbase.outbound.integration.communications.rest.spec.v1.model.Content::getContentId, Function.identity()));
        smsV1.getRecipients().forEach(recipient -> sendSmsV1(recipient, contentMap.get(recipient.getContentId())));
    }

    public Status sendSmsV1(Recipient recipient, Content content) {
        var responseStatus = new Status();
        responseStatus.setRef(recipient.getRef());

        log.debug("Content data: '{}'", content);
        log.debug("Delivering SMS from: '{}' to targets: '{}'", recipient.getFrom(), recipient.getTo());

        try {
            smsV1Mapper.toSmsRequest(recipient, content).forEach(smsNotificationService::sendSms);
            responseStatus.setState(DeliveryCodes.SENT);
        } catch (Exception e) {
            log.error("Communications call failed with error: {}", e.getMessage());
            responseStatus.setError(Error.builder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .message(e.getMessage()).build());
            responseStatus.setState(DeliveryCodes.FAILED);
        }

        return responseStatus;
    }

}

