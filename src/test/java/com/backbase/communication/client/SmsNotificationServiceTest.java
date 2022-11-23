package com.backbase.communication.client;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Status;
import com.backbase.communication.SmsV1Factory;
import com.backbase.communication.SmsV2Factory;
import com.backbase.communication.mapper.SmsV1Mapper;
import com.backbase.communication.mapper.SmsV2Mapper;
import com.backbase.communication.model.SmsRequest;
import com.backbase.communication.model.SmsV1;
import com.backbase.communication.model.SmsV2;
import com.backbase.communication.service.SmsNotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("it")
public class SmsNotificationServiceTest {

    @Autowired
    private SmsNotificationService smsNotificationService;

    @MockBean
    private TwilioClientImpl twilioClient;

    @Autowired
    private SmsV1Mapper smsV1Mapper;

    @Autowired
    private SmsV2Mapper smsV2Mapper;

    @Test
    public void sendSmsV1() {
        final SmsV1 smsV1 = SmsV1Factory.smsV1();
        smsV1Mapper.toSmsRequest(smsV1.getRecipients().get(0), smsV1.getContent().get(0)).forEach(smsNotificationService::sendSms);
        verify(twilioClient, times(1)).sendSms(any());
    }

    @Test
    public void sendSmsV1_failed() {
        final SmsV1 smsV1 = SmsV1Factory.smsV1();
        List<SmsRequest> smsRequests = smsV1Mapper.toSmsRequest(smsV1.getRecipients().get(0), smsV1.getContent().get(0));
        when(twilioClient.sendSms(any())).thenThrow(new RuntimeException());
        SmsRequest smsRequest = smsRequests.get(0);
        assertThrows(RuntimeException.class, () -> smsNotificationService.sendSms(smsRequest));
        verify(twilioClient, times(1)).sendSms(any());
    }

    @Test
    public void sendSmsV2() {
        final SmsV2 smsV2 = SmsV2Factory.smsV2();
        smsV2Mapper.toSmsRequest(smsV2).forEach(smsNotificationService::sendSms);
        verify(twilioClient, times(1)).sendSms(any());
    }

    @Test
    public void sendSmsV2_failed() {
        final SmsV2 smsV2 = SmsV2Factory.smsV2();
        List<SmsRequest> smsRequests = smsV2Mapper.toSmsRequest(smsV2);
        when(twilioClient.sendSms(any())).thenThrow(new RuntimeException());
        SmsRequest smsRequest = smsRequests.get(0);
        assertThrows(RuntimeException.class, () -> smsNotificationService.sendSms(smsRequest));
        verify(twilioClient, times(1)).sendSms(any());
    }

}