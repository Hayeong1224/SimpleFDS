package com.example.core.transfer.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalDateTime;

@Getter // Consumer / Rule 접근용
@NoArgsConstructor // Jackson 역직렬화
@AllArgsConstructor
@ToString // 로그 디버깅용
public class TransferRequestedEvent {

    private Long transferId;
    private Long fromAccountId;
    private Long toAccountId;
    private Long amount;
    private LocalDateTime requestedAt;

}
