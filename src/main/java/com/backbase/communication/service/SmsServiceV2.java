package com.backbase.communication.service;

import com.backbase.communication.mapper.SmsV2Mapper;
import com.backbase.communication.model.Error;
import com.backbase.communication.model.Sendable;
import com.backbase.communication.model.SmsV2;
import com.backbase.communication.model.SmsVersionEnum;
import com.backbase.communication.model.Status;
import com.backbase.communication.util.DeliveryCodes;
import com.backbase.communication.validator.SmsV2Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SmsServiceV2 implements SmsService {
    private final SmsNotificationService smsNotificationService;
    private final SmsV2Validator smsV2Validator;
    private final SmsV2Mapper smsV2Mapper;

    @Override
    public SmsVersionEnum getVersion() {
        return SmsVersionEnum.V2;
    }

    @Override
    public void handleSms(Sendable sendable) {
        SmsV2 smsV2 = (SmsV2) sendable;
        smsV2Validator.validate(smsV2);
        sendSmsV2(smsV2);
    }

    public Status sendSmsV2(SmsV2 smsV2) {
        var responseStatus = new Status();

        log.debug("Content data: '{}'", smsV2.getMessage());
        log.debug("Delivering Sms from: '{}' to targets: '{}'", smsV2.getFrom(), smsV2.getTo());

        try {
            smsV2Mapper.toSmsRequest(smsV2).forEach(smsNotificationService::sendSms);
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
