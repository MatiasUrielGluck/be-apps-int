package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.TransactionController;
import com.uade.beappsint.dto.transaction.TransactionDTO;
import com.uade.beappsint.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Implementation of the TransactionController interface.
 * Provides endpoints for initializing and confirming transactions.
 */
@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<TransactionDTO> createTransaction() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.createTransaction());
    }

    @GetMapping()
    public ResponseEntity<List<TransactionDTO>> getTransactions() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.getTransactions());
    }

    @GetMapping("{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.getTransactionById(id));
    }
}
