package com.example.core.transfer.presentation;

import com.example.core.transfer.application.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<Long> requestTransfer() {
        Long transferId = transferService.requestTransfer(1L, 2L, 10_000L);

        return ResponseEntity.ok(transferId);
    }
}
