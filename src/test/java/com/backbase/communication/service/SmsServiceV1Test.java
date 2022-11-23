package com.backbase.communication.service;

import com.backbase.communication.SmsV1Factory;
import com.backbase.communication.model.SmsV1;
import com.backbase.communication.model.SmsVersionEnum;
import com.backbase.communication.model.Status;
import com.backbase.communication.util.DeliveryCodes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SmsServiceV1Test {

    static {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
    }

    @Autowired
    private SmsServiceV1 smsServiceV1;

    @MockBean
    private SmsNotificationService smsNotificationService;

    @Test
    void getVersion() {
        assertThat(smsServiceV1.getVersion()).isEqualTo(SmsVersionEnum.V1);
    }

    @Test
    void handleSms() {
        SmsV1 smsV1 = SmsV1Factory.smsV1();
        smsServiceV1.handleSms(smsV1);
        verify(smsNotificationService,times(1)).sendSms(any());
    }

    @Test
    void sendSmsV1_success() {
        SmsV1 smsV1 = SmsV1Factory.smsV1();
        Status status = smsServiceV1.sendSmsV1(smsV1.getRecipients().get(0), smsV1.getContent().get(0));
        assertThat(status.getState()).isEqualTo(DeliveryCodes.SENT);
    }

    @Test
    void sendSmsV1_failed() {
        SmsV1 smsV1 = SmsV1Factory.smsV1();
        when(smsNotificationService.sendSms(any())).thenThrow(new RuntimeException());
        Status status = smsServiceV1.sendSmsV1(smsV1.getRecipients().get(0), smsV1.getContent().get(0));
        assertThat(status.getState()).isEqualTo(DeliveryCodes.FAILED);
    }
}