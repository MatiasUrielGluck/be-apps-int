package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.TransactionController;
import com.uade.beappsint.dto.transaction.TransactionDTO;
import com.uade.beappsint.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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





    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        /*transactionService.deleteTransaction(id);*/
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByStatus(@PathVariable String status) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        /*return ResponseEntity
                .status(HttpStatus.OK);
                .body(transactionService.getTransactionsByStatus(status));*/
    }

    @GetMapping("/amount/{minAmount}/{maxAmount}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAmountRange(@PathVariable Double minAmount, @PathVariable Double maxAmount) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        /*return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.getTransactionsByAmountRange(minAmount, maxAmount));*/
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        /*return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.getTransactionsByCustomerId(customerId));*/
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<TransactionDTO> updateTransactionStatus(@PathVariable Long id, @RequestBody String status) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        /*return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.updateTransactionStatus(id, status));*/
    }

    @GetMapping("/latest")
    public ResponseEntity<List<TransactionDTO>> getLatestTransactions() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        /*return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.getLatestTransactions());*/
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<TransactionDTO>> createBulkTransactions(@RequestBody List<TransactionDTO> transactionDTOs) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        /*return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionService.createBulkTransactions(transactionDTOs));*/
    }
}

