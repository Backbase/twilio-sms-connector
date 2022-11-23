package com.backbase.communication.mapper;

import com.backbase.communication.model.SmsRequest;
import com.backbase.communication.model.SmsV2;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SmsV2Mapper {
    private static final String SPLIT_REGEX = ":";

    public List<SmsRequest> toSmsRequest(SmsV2 smsV2) {
        return smsV2.getTo().stream().map(to -> SmsRequest.builder().message(smsV2.getMessage())
                .fromNumber(new PhoneNumber(smsV2.getFrom()))
                .toNumber(new PhoneNumber(to.split(SPLIT_REGEX).length > 1 ? to.split(SPLIT_REGEX)[1] : to))
                .build()).toList();
    }
}
