package com.example.core.fds.domain;

import com.example.core.transfer.event.TransferResultType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FraudDetectionLog {
    @Id
    @GeneratedValue
    private Long id;

    private Long transferId;

    private int score;

    private String reasons;

    @Enumerated(EnumType.STRING)
    private TransferResultType resultType;

    private LocalDateTime detectedAt;

    public FraudDetectionLog(
            Long transferId,
            int score,
            String reasons,
            TransferResultType resultType,
            LocalDateTime detectedAt
    ) {
        this.transferId = transferId;
        this.score = score;
        this.reasons = reasons;
        this.resultType = resultType;
        this.detectedAt = detectedAt;
    }
}
