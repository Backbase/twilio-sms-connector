package com.backbase.communication.model;

import com.twilio.type.PhoneNumber;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class SmsRequest {

    private PhoneNumber fromNumber;
    private PhoneNumber toNumber;
    private String message;

}
