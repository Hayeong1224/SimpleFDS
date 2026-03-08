package com.example.core.fds.consumer;

import com.example.core.fds.service.FdsService;
import com.example.core.kafka.KafkaTopics;
import com.example.core.transfer.event.TransferRequestedEvent;
import com.example.core.transfer.event.TransferResultEvent;
import com.example.core.transfer.messaging.TransferEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class TransferRequestedConsumer {
    private static final Logger log =
            LoggerFactory.getLogger(TransferRequestedConsumer.class);

    private final FdsService fdsService;
    private final TransferEventPublisher transferEventPublisher;

    public TransferRequestedConsumer(FdsService fdsService,
                                     TransferEventPublisher transferEventPublisher) {

        this.fdsService = fdsService;
        this.transferEventPublisher = transferEventPublisher;
    }

    @KafkaListener(
            topics = KafkaTopics.TRANSFER_REQUESTED,
            containerFactory = "transferRequestedKafkaListenerContainerFactory"
    )
    public void consume(TransferRequestedEvent event) {
        log.info("[FDS] TransferRequestedEvent 수신: {}", event);
        TransferResultEvent result = fdsService.handle(event);
        log.info("[FDS] detection result: {}", result);
        transferEventPublisher.publishTransferResult(result);
    }
}