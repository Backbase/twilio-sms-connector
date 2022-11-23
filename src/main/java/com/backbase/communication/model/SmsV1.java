package com.backbase.communication.model;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SmsV1 extends BatchResponse implements Sendable {
    @Override
    public SmsVersionEnum getVersion() {
        return SmsVersionEnum.V1;
    }

    public SmsV1(BatchResponse batchResponse) {
        this.setContent(batchResponse.getContent());
        this.setRecipients(batchResponse.getRecipients());
    }
}
