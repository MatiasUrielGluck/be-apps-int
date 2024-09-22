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

/**
 * Implementation of the TransactionController interface.
 * Provides endpoints for initializing and confirming transactions.
 */
@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;

    /**
     * Initializes a transaction.
     * Creates the transaction in the system without concreting it.
     * Returns the transaction so that the user can review it and then confirm it.
     *
     * @return a ResponseEntity containing the transaction data transfer object
     */
    @PostMapping("/initialize")
    public ResponseEntity<TransactionDTO> initializeTransaction() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.initializeTransaction());
    }

    /**
     * Confirms a transaction.
     * Handles the payment confirming the transaction.
     *
     * @param transactionId the ID of the transaction to confirm
     * @return a ResponseEntity containing the confirmed transaction data transfer object
     */
    @PostMapping("/confirm/{transactionId}")
    public ResponseEntity<TransactionDTO> confirmTransaction(@PathVariable Long transactionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.confirmTransaction(transactionId));
    }
}
