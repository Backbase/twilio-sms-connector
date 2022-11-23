package com.backbase.communication.communication;

import com.backbase.communication.SmsV1Factory;
import com.backbase.communication.SmsV2Factory;
import com.backbase.communication.service.SmsNotificationService;
import com.backbase.communication.validator.SmsV1Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("it")
public class CommunicationChannelConsumerTest {

    @Autowired
    CommunicationChannelConsumer communicationChannelConsumer;

    @Autowired
    SmsV1Validator smsV1Validator;

    @MockBean
    SmsNotificationService smsNotificationService;

    @Test
    public void acceptSmsV1Test() {
        communicationChannelConsumer.accept(SmsV1Factory.smsV1());
        verify(smsNotificationService, times(1)).sendSms(any());
    }

    @Test
    public void acceptSmsV2Test() {
        communicationChannelConsumer.accept(SmsV2Factory.smsV2());
        verify(smsNotificationService, times(1)).sendSms(any());
    }
}
