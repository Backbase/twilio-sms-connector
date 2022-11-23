package com.backbase.communication.service;

import com.backbase.communication.client.TwilioClientImpl;
import com.backbase.communication.model.SmsRequest;
import com.backbase.communication.util.DeliveryCodes;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SmsNotificationService {

    private final TwilioClientImpl twilioClient;

    public Status sendSms(SmsRequest smsRequest) {
        log.debug("sms request data: '{}'", smsRequest);
        log.debug("Delivering SMS from: '{}' to targets: '{}'", smsRequest.getFromNumber(), smsRequest.getToNumber());
        twilioClient.sendSms(smsRequest);
        return new Status().status(DeliveryCodes.SENT);
    }


}
