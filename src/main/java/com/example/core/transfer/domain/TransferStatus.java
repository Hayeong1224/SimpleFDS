package com.example.core.transfer.domain;

public enum TransferStatus {
    PENDING,        // FDS 판단 대기
    APPROVED,       // FDS 승인
    REJECTED,       // FDS 차단
    COMPLETED,      // 실제 송금 완료
    FAILED          // 시스템 실패
}
