package com.backbase.communication.client;

import com.backbase.communication.model.SmsRequest;
import com.twilio.rest.api.v2010.account.Message;

public interface TwilioClient {
    Message sendSms(SmsRequest smsRequest);
}
