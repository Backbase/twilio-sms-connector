package com.backbase.communication.service;

import com.backbase.communication.SmsV2Factory;
import com.backbase.communication.model.SmsV2;
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
class SmsServiceV2Test {

    static {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
    }

    @Autowired
    private SmsServiceV2 smsServiceV2;

    @MockBean
    private SmsNotificationService smsNotificationService;

    @Test
    void getVersion() {
        assertThat(smsServiceV2.getVersion()).isEqualTo(SmsVersionEnum.V2);
    }

    @Test
    void handleSms() {
        SmsV2 smsV2 = SmsV2Factory.smsV2();
        smsServiceV2.handleSms(smsV2);
        verify(smsNotificationService, times(1)).sendSms(any());
    }

    @Test
    void sendSmsV1_success() {
        SmsV2 smsV2 = SmsV2Factory.smsV2();
        Status status = smsServiceV2.sendSmsV2(smsV2);
        assertThat(status.getState()).isEqualTo(DeliveryCodes.SENT);
    }

    @Test
    void sendSmsV1_failed() {
        SmsV2 smsV2 = SmsV2Factory.smsV2();
        when(smsNotificationService.sendSms(any())).thenThrow(new RuntimeException());
        Status status = smsServiceV2.sendSmsV2(smsV2);
        assertThat(status.getState()).isEqualTo(DeliveryCodes.FAILED);
    }
}