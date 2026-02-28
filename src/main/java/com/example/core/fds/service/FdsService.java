package com.example.core.fds.service;

import com.example.core.fds.model.DetectionResult;
import com.example.core.fds.rule.RuleEngine;
import com.example.core.transfer.event.TransferRequestedEvent;
import com.example.core.transfer.event.TransferResultEvent;
import com.example.core.transfer.event.TransferResultType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FdsService {

    private final RuleEngine ruleEngine;

    public TransferResultEvent handle(TransferRequestedEvent event) {

        DetectionResult result = ruleEngine.evaluate(event);

        if (result.suspicious()) {
            String reason = result.reasons().isEmpty() ? "UNKNOWN" : String.join(",", result.reasons());

            return new TransferResultEvent(
                    event.getTransferId(),
                    TransferResultType.REJECTED,
                    reason
            );
        }

        return new TransferResultEvent(
                event.getTransferId(),
                TransferResultType.APPROVED,
                null
        );
    }
}