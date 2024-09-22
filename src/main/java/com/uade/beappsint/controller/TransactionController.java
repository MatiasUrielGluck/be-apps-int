package com.uade.beappsint.controller;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

/**
 * Interface for transaction-related endpoints.
 * Provides methods for initializing and confirming transactions.
 */
@Tag(name = "Transaction", description = "Endpoints for the transaction system")
public interface TransactionController {

    /**
     * Initializes a transaction.
     * Creates the transaction in the system without concreting it.
     * Returns the transaction so that the user can review it and then confirm it.
     *
     * @return a ResponseEntity containing the transaction data transfer object
     */
    @Operation(
            summary = "Initialize a transaction",
            description = "Creates the transaction in the system without concreting it. Returns the transaction so that the user can review it and then confirm it."
    )
    ResponseEntity<TransactionDTO> initializeTransaction();

    /**
     * Confirms a transaction.
     * Handles the payment confirming the transaction.
     *
     * @param transactionId the ID of the transaction to confirm
     * @return a ResponseEntity containing the confirmed transaction data transfer object
     */
    @Operation(
            summary = "Confirm a transaction",
            description = "Handle the payment confirming the transaction."
    )
    ResponseEntity<TransactionDTO> confirmTransaction(Long transactionId);
}
