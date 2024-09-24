package com.uade.beappsint.controller;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Transaction", description = "Endpoints for the transaction system")
public interface TransactionController {
    @Operation(
            summary = "Creates a transaction",
            description = "Creates the transaction in the system."
    )
    ResponseEntity<TransactionDTO> createTransaction();

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
