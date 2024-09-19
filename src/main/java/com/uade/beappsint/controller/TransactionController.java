package com.uade.beappsint.controller;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import org.springframework.http.ResponseEntity;

public interface TransactionController {
    ResponseEntity<TransactionDTO> initializeTransaction();

    ResponseEntity<TransactionDTO> confirmTransaction(Long transactionId);
}
