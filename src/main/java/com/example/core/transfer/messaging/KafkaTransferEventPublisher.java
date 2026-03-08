package com.example.core.transfer.messaging;

import com.example.core.kafka.KafkaTopics;
import com.example.core.transfer.event.TransferRequestedEvent;
import com.example.core.transfer.event.TransferResultEvent;
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
                KafkaTopics.TRANSFER_REQUESTED,
                event.getTransferId().toString(),
                event
        );
    }

    @Override
    public void publishTransferResult(TransferResultEvent event) {
        kafkaTemplate.send(
                KafkaTopics.TRANSFER_RESULT,
                event.getTransferId().toString(),
                event
        );
    }
}