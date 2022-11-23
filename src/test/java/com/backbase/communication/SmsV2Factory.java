package com.backbase.communication;

import com.backbase.communication.model.SmsV2;

import java.util.Set;

public class SmsV2Factory {
    public static SmsV2 smsV2(){
        SmsV2 smsV2 = new SmsV2();
        smsV2.setFrom("+123456789");
        smsV2.setTo(Set.of("+01456789"));
        smsV2.setMessage("This is your {otp}");
        return smsV2;
    }

    public static SmsV2 emptyToSmsV2(){
        SmsV2 smsV2 = new SmsV2();
        smsV2.setFrom("+123456789");
        smsV2.setTo(Set.of());
        smsV2.setMessage("This is your {otp}");
        return smsV2;
    }

    public static SmsV2 emptyFromSmsV2(){
        SmsV2 smsV2 = new SmsV2();
        smsV2.setTo(Set.of("+01456789"));
        smsV2.setMessage("This is your {otp}");
        return smsV2;
    }
}
