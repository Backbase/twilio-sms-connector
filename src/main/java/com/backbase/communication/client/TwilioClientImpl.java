package com.backbase.communication.client;

import com.backbase.communication.model.SmsRequest;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.twilio.rest.api.v2010.account.Message.creator;

@Slf4j
@Component
public class TwilioClientImpl implements TwilioClient {

    @Override
    public Message sendSms(SmsRequest smsRequest) {
    	log.debug("SmsRequest : '{}'", smsRequest.toString());
        return creator(smsRequest.getToNumber(), smsRequest.getFromNumber(), smsRequest.getMessage()).create();
    }
}