package com.example.core.fds.service;

import com.example.core.fds.model.DetectionResult;
import com.example.core.fds.rule.RuleEngine;
import com.example.core.fds.rule.Rules;
import com.example.core.transfer.event.TransferRequestedEvent;
import com.example.core.transfer.event.TransferResultEvent;
import com.example.core.transfer.event.TransferResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FdsService {

    private final RuleEngine ruleEngine;
    private static final int REJECT_SCORE_THRESHOLD = 80;

    public TransferResultEvent handle(TransferRequestedEvent event) {

        DetectionResult result = ruleEngine.evaluate(event);

        if (!result.suspicious()) {
            return new TransferResultEvent(
                    event.getTransferId(),
                    TransferResultType.APPROVED,
                    null,
                    LocalDateTime.now()
            );
        }

        String reason = result.reasons().isEmpty() ? "UNKNOWN" : String.join(",", result.reasons());

        if (shouldReject(result)) {
            return new TransferResultEvent(
                    event.getTransferId(),
                    TransferResultType.REJECTED,
                    null,
                    LocalDateTime.now()
            );
        }

        return new TransferResultEvent(
                event.getTransferId(),
                TransferResultType.REQUIRE_ADDITIONAL_AUTH,
                reason,
                LocalDateTime.now()
        );
    }

    private boolean shouldReject(DetectionResult result) {
        return hasImmediateRejectReason(result) || result.score() >= REJECT_SCORE_THRESHOLD;
    }

    private boolean hasImmediateRejectReason(DetectionResult result) {
        return result.reasons().contains(Rules.SELF_TRANSFER);
    }
}