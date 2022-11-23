package com.backbase.communication.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Message<T> {

    private UUID trackingId;
    private String deliveryChannel;
    private Integer priority;
    private String tag;
    private T payload;
    private ZonedDateTime expiresAt;
    private String callbackUrl;
}
