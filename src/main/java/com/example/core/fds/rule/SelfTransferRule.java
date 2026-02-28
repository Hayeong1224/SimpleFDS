package com.example.core.fds.rule;

import com.example.core.transfer.event.TransferRequestedEvent;
import org.springframework.stereotype.Component;

@Component
public class SelfTransferRule implements FdsRule{
    @Override
    public String code() {
        return "SELF_TRANSFER";
    }

    @Override
    public int weight() {
        return 80;
    }

    @Override
    public boolean matches(TransferRequestedEvent event) {
        return event.getFromAccountId().equals(event.getToAccountId());
    }
}
