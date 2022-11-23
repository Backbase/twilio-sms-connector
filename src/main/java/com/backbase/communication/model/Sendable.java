package com.backbase.communication.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.boot.jackson.JsonMixin;

@JsonMixin({SmsV1.class, SmsV2.class})
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = SmsV1.class),
                @JsonSubTypes.Type(value = SmsV2.class)

        })
public interface Sendable {
    SmsVersionEnum getVersion();
}
