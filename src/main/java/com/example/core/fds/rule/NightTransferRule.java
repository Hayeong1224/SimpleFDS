package com.example.core.fds.rule;

import com.example.core.transfer.event.TransferRequestedEvent;
import org.springframework.stereotype.Component;

@Component
public class NightTransferRule implements FdsRule{
    @Override
    public String code() {
        return "NIGHT_TRANSFER";
    }

    @Override
    public int weight() {
        return 30;
    }

    @Override
    public boolean matches(TransferRequestedEvent event) {
        int h = event.getRequestedAt().getHour();
        return h >= 0 && h <= 5; // 새벽 12시 ~ 5시
    }
}
