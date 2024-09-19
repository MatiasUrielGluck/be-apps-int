package com.uade.beappsint.controller;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public interface TransactionController {
    @PostMapping("/initialize")
    ResponseEntity<TransactionDTO> initializeTransaction();

    @PostMapping("/confirm/{transactionId}")
    ResponseEntity<TransactionDTO> confirmTransaction(@PathVariable Long transactionId);
}
