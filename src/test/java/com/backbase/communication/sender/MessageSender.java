package com.backbase.communication.sender;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.communication.config.MessageChannelProperties;
import com.backbase.communication.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Range;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * The {@link MessageSender} dispatches messages to the correct destination depending on deliveryChannel and priority.
 * <h1>Thread Safety:</h1>
 * For {@link MessageSender} clients, it is expected that multithreaded usage is handled by the sender itself, clients
 * should not handle that themselves by any means
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MessageSender {

    public static final String TRACKING_ID_HEADER = "trackingId";
    private static final String EXPIRES_AT_HEADER = "expiresAt";
    private final MessageSenderProperties messageSenderProperties;
    private final MessageChannelProperties messageChannelProperties;
    /**
     * Spring Stream StreamBridge. This requires config that conflicts with standard Backbase config
     */
    private final StreamBridge streamBridge;

    /**
     * Sends the message that is stored inside the payload attribute of the message to the appropriate queue.
     *
     * @param message Message
     * @throws BadRequestException if no suitable binding has been configured for the channel and priority
     */
    public void sendMessage(Message<?> message) {

        log.debug("Sending message[{}] to broker", message);

        String channelName = message.getDeliveryChannel();
        Integer priority = message.getPriority();

        Map<Range<Integer>, String> channel =
            messageChannelProperties.getChannels().get(channelName);
        if (channel == null) {
            throw new BadRequestException(String.format("Channel %s not configured", channelName));
        }

        Range<Integer> range =
            channel.keySet().stream()
                .filter(e -> e.contains(priority))
                .findFirst()
                .orElseThrow(() -> {
                    throw new BadRequestException(
                        String.format("Priority %d not configured for channel %s", priority, channelName));
                });

        String channelBinding = channel.get(range);

        if (!messageSenderProperties.getBindings().containsKey(channelBinding)) {
            throw new BadRequestException(
                String.format("Channel binding %s is not configured for channel %s", channelBinding, channelName));
        }

        log.debug("channelBinding resolved as {} for message {}", channelBinding, message);

        streamBridge.send(channelBinding, createMessage(message));
    }

    private org.springframework.messaging.Message<?> createMessage(Message<?> message) {
        return MessageBuilder
            .withPayload(message.getPayload())
            .setHeader(EXPIRES_AT_HEADER, message.getExpiresAt())
            .setHeader(TRACKING_ID_HEADER, message.getTrackingId()).build();
    }

}
