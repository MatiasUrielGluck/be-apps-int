package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.TransactionController;
import com.uade.beappsint.dto.transaction.TransactionDTO;
import com.uade.beappsint.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/initialize")
    public ResponseEntity<TransactionDTO> initializeTransaction() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.initializeTransaction());
    }

    @PostMapping("/confirm/{transactionId}")
    public ResponseEntity<TransactionDTO> confirmTransaction(@PathVariable Long transactionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.confirmTransaction(transactionId));
    }
}
