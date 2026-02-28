package com.example.core.fds.rule;

import com.example.core.transfer.event.TransferRequestedEvent;

public interface FdsRule {
    String code();
    int weight(); // 점수 가중치
    boolean matches(TransferRequestedEvent event);
}
