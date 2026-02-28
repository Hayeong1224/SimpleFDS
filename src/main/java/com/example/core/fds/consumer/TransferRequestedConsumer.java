package com.example.core.fds.consumer;

import com.example.core.fds.service.FdsService;
import com.example.core.transfer.event.TransferRequestedEvent;
import com.example.core.transfer.event.TransferResultEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class TransferRequestedConsumer {
    private static final Logger log =
            LoggerFactory.getLogger(TransferRequestedConsumer.class);

    private final FdsService fdsService;

    public TransferRequestedConsumer(FdsService fdsService) {
        this.fdsService = fdsService;
    }

//    @KafkaListener(
//            topics = "transfer.requested",
//            groupId = "fds-group-debug"
//    )
//    public void consume(String message) {
//        log.info("RAW MESSAGE: {}", message);
//    }


    @KafkaListener(
            topics = "transfer.requested",
            containerFactory = "transferRequestedKafkaListenerContainerFactory"
    )
    public void consume(TransferRequestedEvent event) {
        log.info("[FDS] TransferRequestedEvent 수신: {}", event);
        TransferResultEvent result = fdsService.handle(event);
        log.info("[FDS] detection result: {}", result);
    }
}