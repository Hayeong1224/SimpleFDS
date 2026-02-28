package com.example.core.transfer.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "transfer")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long fromAccountId;

    @Column(nullable = false)
    private Long toAccountId;

    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime completedAt;

    private String rejectReason; // FDS 차단 사유 (룰명, 점수 초과 등)

    protected Transfer() {
        // JPA용
    }

    private Transfer(Long fromAccountId, Long toAccountId, Long amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.status = TransferStatus.PENDING;
        this.requestedAt = LocalDateTime.now();
    }

    // 생성
    public static Transfer create(Long fromAccountId, Long toAccountId, Long amount) {
        validateAmount(amount);
        return new Transfer(fromAccountId, toAccountId, amount);
    }

    // 상태 전이
    public void approve() {
        assertStatus(TransferStatus.PENDING);
        this.status = TransferStatus.APPROVED;
    }

    public void reject(String reason) {
        assertStatus(TransferStatus.PENDING);
        this.status = TransferStatus.REJECTED;
        this.rejectReason = reason;
    }

    public void complete() {
        assertStatus(TransferStatus.APPROVED);
        this.status = TransferStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public void fail() {
        assertStatus(TransferStatus.APPROVED);
        this.status = TransferStatus.FAILED;
    }

    // 검증
    private void assertStatus(TransferStatus expected) { // 현 상태 확인
        if (this.status != expected) {
            throw new IllegalStateException(
                    "Invalid state transition: current=" + this.status + ", expected=" + expected
            );
        }
    }

    private static void validateAmount(Long amount) { // 가능한 송금 금액인지 확인
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
    }

}
