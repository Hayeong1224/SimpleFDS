package com.example.core.transfer.consumer;

import com.example.core.kafka.KafkaTopics;
import com.example.core.transfer.event.TransferResultEvent;
import com.example.core.transfer.event.TransferResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransferResultConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(TransferResultConsumer.class);

    @KafkaListener(
            topics = KafkaTopics.TRANSFER_RESULT,
            containerFactory = "transferResultKafkaListenerContainerFactory"
    )
    public void consume(TransferResultEvent event) {
        log.info("[TRANSFER] TransferResultEvent 수신: {}", event);

        if (event.getResultType() == TransferResultType.APPROVED) {
            log.info("[TRANSFER] 송금 승인 - transferId={}", event.getTransferId());
        }

        else if (event.getResultType() == TransferResultType.REQUIRE_ADDITIONAL_AUTH) {
            log.info("[TRANSFER] 추가인증 필요 - transferId={}, reason={}", event.getTransferId(), event.getReason());
            return;
        }

        else if (event.getResultType() == TransferResultType.REJECTED) {
            log.info("[TRANSFER] 송금 거절 - transferId={}, reason={}", event.getTransferId(), event.getReason());
        }
    }
}
