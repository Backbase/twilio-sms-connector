package com.backbase.communication.service;

import com.backbase.communication.model.SmsVersionEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmsStrategyFactoryTest {

    static {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
    }

    @Autowired
    private SmsStrategyFactory smsStrategyFactory;

    @Test
    void findSmsStrategy() {
        assertThat(smsStrategyFactory.findSmsStrategy(SmsVersionEnum.V1)).isNotNull();
        assertThat(smsStrategyFactory.findSmsStrategy(SmsVersionEnum.V2)).isNotNull();
    }
}