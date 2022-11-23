package com.backbase.communication.service;

import com.backbase.communication.model.Sendable;
import com.backbase.communication.model.SmsVersionEnum;

public interface SmsService {
    SmsVersionEnum getVersion();

    void handleSms(Sendable sendable);
}
