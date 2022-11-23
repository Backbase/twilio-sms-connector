package com.backbase.communication.model;

import com.backbase.communication.event.spec.v1.SmsChannelEvent;

public class SmsV2 extends SmsChannelEvent implements Sendable {
    @Override
    public SmsVersionEnum getVersion() {
        return SmsVersionEnum.V2;
    }
}
