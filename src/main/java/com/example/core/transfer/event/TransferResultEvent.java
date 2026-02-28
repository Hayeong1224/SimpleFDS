package com.example.core.transfer.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TransferResultEvent {

    private final Long transferId;
    private final TransferResultType resultType;
    private final String reason;
    private final LocalDateTime occuredAt;

    public TransferResultEvent (Long transferId, TransferResultType resultType, String reason) {
        this.transferId = transferId;
        this.resultType = resultType;
        this.reason = reason;
        this.occuredAt = LocalDateTime.now();
    }

}
