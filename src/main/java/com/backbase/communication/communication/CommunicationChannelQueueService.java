package com.backbase.communication.communication;

import com.backbase.buildingblocks.commns.service.AbstractPriorityQueueService;
import com.backbase.buildingblocks.multitenancy.TenantProvider;
import com.backbase.communication.model.Sendable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommunicationChannelQueueService extends AbstractPriorityQueueService<Sendable> {

    public CommunicationChannelQueueService(
            @Value("${backbase.sms.worker-count}") int numberOfWorkers,
            CommunicationChannelConsumer communicationChannelConsumer,
            TenantProvider tenantProvider
    ) {
        super(numberOfWorkers, communicationChannelConsumer, tenantProvider);
    }
}
