package com.example.core.transfer.messaging;

import com.example.core.transfer.event.TransferRequestedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaTransferEventPublisher implements TransferEventPublisher{
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaTransferEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishTransferRequested(TransferRequestedEvent event) {
        kafkaTemplate.send(
                "transfer.requested",
                event.getTransferId().toString(),
                event
        );
    }
}
