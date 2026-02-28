package com.example.core.fds.rule;

import com.example.core.transfer.event.TransferRequestedEvent;
import org.springframework.stereotype.Component;

@Component
public class HighAmountRule implements FdsRule{
    @Override
    public String code() { return "HIGH_AMOUNT"; }

    @Override
    public int weight() { return 50; }

    @Override
    public boolean matches(TransferRequestedEvent event) {
        return event.getAmount() >= 3_000_000; // 300만원 이상 - 고액
    }
}
