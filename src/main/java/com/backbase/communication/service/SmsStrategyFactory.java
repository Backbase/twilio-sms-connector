package com.backbase.communication.service;

import com.backbase.communication.model.SmsVersionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Component
public class SmsStrategyFactory {
    private final Map<SmsVersionEnum, SmsService> smsStrategyMap;

    @Autowired
    public SmsStrategyFactory(Set<SmsService> smsServices) {
        smsStrategyMap = new EnumMap<>(SmsVersionEnum.class);
        smsServices.forEach(smsService -> smsStrategyMap.put(smsService.getVersion(), smsService));
    }

    public SmsService findSmsStrategy(SmsVersionEnum smsVersionEnum) {
        if (!smsStrategyMap.containsKey(smsVersionEnum))
            throw new IllegalArgumentException(MessageFormat.format("Invalid sms version: {0}", smsVersionEnum.getValue()));
        return smsStrategyMap.get(smsVersionEnum);
    }
}
