package com.example.core.transfer.messaging;

import com.example.core.transfer.event.TransferRequestedEvent;
import com.example.core.transfer.event.TransferResultEvent;

// TransferService는 Kafka를 몰라도 되게 인터페이스로 만들자!
public interface TransferEventPublisher {
    void publishTransferRequested(TransferRequestedEvent event);
    void publishTransferResult(TransferResultEvent event);
}
