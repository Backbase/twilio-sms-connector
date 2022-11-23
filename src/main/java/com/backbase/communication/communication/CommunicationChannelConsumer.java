package com.backbase.communication.communication;

import com.backbase.communication.model.Sendable;
import com.backbase.communication.service.SmsStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommunicationChannelConsumer implements Consumer<Sendable> {
    private final SmsStrategyFactory smsStrategyFactory;

    @Override
    public void accept(Sendable sendable) {
        log.debug("Sms payload: '{}'", sendable.toString());
        smsStrategyFactory.findSmsStrategy(sendable.getVersion()).handleSms(sendable);
    }
}
