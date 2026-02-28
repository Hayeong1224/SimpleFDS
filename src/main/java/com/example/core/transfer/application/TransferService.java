package com.example.core.transfer.application;

import com.example.core.transfer.domain.Transfer;
import com.example.core.transfer.domain.TransferRepository;
import com.example.core.transfer.domain.TransferStatus;
import com.example.core.transfer.event.TransferRequestedEvent;
import com.example.core.transfer.messaging.TransferEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransferEventPublisher eventPublisher;

    public TransferService(
            TransferRepository transferRepository,
            TransferEventPublisher eventPublisher
    ) {
        this.transferRepository = transferRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    // 송금 요청 생성
    public Long requestTransfer(Long fromAccountId, Long toAccountId, Long amount) {

        // 1. Transfer 생성
        Transfer transfer = Transfer.create(fromAccountId, toAccountId, amount);

        // 2. 저장
        transferRepository.save(transfer);

        // 3. 이벤트 발행 (kafka)
        TransferRequestedEvent event =
                new TransferRequestedEvent(
                        transfer.getId(),
                        transfer.getFromAccountId(),
                        transfer.getToAccountId(),
                        transfer.getAmount(),
                        LocalDateTime.now()
                );

        eventPublisher.publishTransferRequested(event);

        return transfer.getId();
    }
}


