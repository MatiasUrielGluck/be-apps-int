package com.uade.beappsint.controller;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
            summary = "Creates a transaction",
            description = "Creates the transaction in the system."
    )
    ResponseEntity<TransactionDTO> createTransaction();

    /**
     * Confirms a transaction.
     * Handles the payment confirming the transaction.
     *
     * @param transactionId the ID of the transaction to confirm
     * @return a ResponseEntity containing the confirmed transaction data transfer object
     */
    @Operation(
            summary = "Get all users transactions summarized.",
            description = "Gets the basic info of all transactions."
    )
    ResponseEntity<List<TransactionDTO>> getTransactions();

    @Operation(
            summary = "Get users transaction by id.",
            description = "Returns all the transaction information fetching the each item detail."
    )
    ResponseEntity<TransactionDTO> getTransactionById(Long id);
}
