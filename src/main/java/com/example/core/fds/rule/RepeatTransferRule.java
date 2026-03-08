package com.example.core.fds.rule;

import com.example.core.fds.redis.TransferCountTracker;
import com.example.core.transfer.event.TransferRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepeatTransferRule implements FdsRule{

    private final TransferCountTracker transferCountTracker;
    @Override
    public String code() {
        return Rules.REPEAT_TRANSFER;
    }

    @Override
    public int weight() {
        return 70;
    }

    @Override
    public boolean matches(TransferRequestedEvent event) {
        long count = transferCountTracker.increaseAndGetCount(event.getFromAccountId());
        return count >= 5; // 5분 이내 5번 이상 송금
    }
}
