package com.example.core.transfer.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // Jackson 역직렬화
@AllArgsConstructor
@ToString // 로그 디버깅용
public class TransferResultEvent {

    private Long transferId;
    private TransferResultType resultType;
    private String reason;
    private LocalDateTime occuredAt;

}
