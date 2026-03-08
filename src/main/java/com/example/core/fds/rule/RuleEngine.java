package com.example.core.fds.rule;

import com.example.core.fds.model.DetectionResult;
import com.example.core.transfer.event.TransferRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RuleEngine {

    private final List<FdsRule> rules;

    public DetectionResult evaluate(TransferRequestedEvent event) {
        int score = 0;
        List<String> reasons = new ArrayList<>();

        for (FdsRule rule : rules) {
            if (rule.matches(event)) {
                score += rule.weight();
                reasons.add(rule.code());
            }
        }

        return new DetectionResult(score, reasons);
    }
}
