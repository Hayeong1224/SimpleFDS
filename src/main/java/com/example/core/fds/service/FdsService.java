package com.example.core.fds.service;

import com.example.core.transfer.event.TransferRequestedEvent;
import com.example.core.transfer.event.TransferResultEvent;
import com.example.core.transfer.event.TransferResultType;
import org.springframework.stereotype.Service;

@Service
public class FdsService {

    public TransferResultEvent handle(TransferRequestedEvent event) {

        // todo: Redis 기반 Rule 판단
        boolean suspicious = false;

        if (suspicious) {
            return new TransferResultEvent(
                    event.getTransferId(),
                    TransferResultType.REJECTED,
                    "REPEAT_TRANSFER" // 일단 단기간 반복 송금만!
            );
        }

        return new TransferResultEvent(
                event.getTransferId(),
                TransferResultType.APPROVED,
                null
        );
    }
}
