package com.example.core.fds.service;

import com.example.core.fds.domain.FraudDetectionLog;
import com.example.core.fds.domain.FraudDetectionLogRepository;
import com.example.core.fds.model.DetectionResult;
import com.example.core.fds.rule.RuleEngine;
import com.example.core.transfer.event.TransferRequestedEvent;
import com.example.core.transfer.event.TransferResultEvent;
import com.example.core.transfer.event.TransferResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FdsService {

    private final RuleEngine ruleEngine;
    private final FraudDetectionLogRepository fraudDetectionLogRepository;
    private static final int ADDITIONAL_AUTH_SCORE_THRESHOLD = 60;
    private static final int REJECT_SCORE_THRESHOLD = 80;

    public TransferResultEvent handle(TransferRequestedEvent event) {

        DetectionResult result = ruleEngine.evaluate(event);
        TransferResultType resultType = scoreToResultType(result.score());
        LocalDateTime now = LocalDateTime.now();

        if (resultType != TransferResultType.APPROVED) {
            String reason = result.reasons().isEmpty() ? "UNKNOWN" : String.join(",", result.reasons());

            // 이상 거래 기록 남기기
            fraudDetectionLogRepository.save(
                    new FraudDetectionLog(
                            event.getTransferId(),
                            result.score(),
                            reason,
                            resultType,
                            now
                    )
            );

            return new TransferResultEvent(
                    event.getTransferId(),
                    resultType,
                    reason,
                    now
            );
        }
        else { // 승인
            return new TransferResultEvent(
                    event.getTransferId(),
                    resultType,
                    null,
                    now
            );
        }
    }

    private TransferResultType scoreToResultType (int score) {
        if (score >= REJECT_SCORE_THRESHOLD) {
            return TransferResultType.REJECTED;
        }

        if (score >= ADDITIONAL_AUTH_SCORE_THRESHOLD) {
            return TransferResultType.REQUIRE_ADDITIONAL_AUTH;
        }

        return TransferResultType.APPROVED;
    }
}