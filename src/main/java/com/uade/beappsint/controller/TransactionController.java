package com.uade.beappsint.controller;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Transaction", description = "Endpoints for the transaction system")
public interface TransactionController {
    @Operation(
            summary = "Initialize a transaction",
            description = "Creates the transaction in the system without concreting it. Returns the transaction so that the user can review it and then confirm it."
    )
    ResponseEntity<TransactionDTO> initializeTransaction();

    @Operation(
            summary = "Confirm a transaction",
            description = "Handle the payment confirming the transaction."
    )
    ResponseEntity<TransactionDTO> confirmTransaction(Long transactionId);
}
