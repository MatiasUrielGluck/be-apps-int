package com.uade.beappsint.service;

import com.uade.beappsint.dto.transaction.TransactionDTO;

import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction();

    List<TransactionDTO> getTransactions();

    TransactionDTO getTransactionById(Long id);





    /*
    List<TransactionDTO> createBulkTransactions(List<TransactionDTO> transactionDTOs);

    List<TransactionDTO> getLatestTransactions();

    TransactionDTO updateTransactionStatus(Long id, String status);

    List<TransactionDTO> getTransactionsByCustomerId(Long customerId);

    List<TransactionDTO> getTransactionsByAmountRange(Double minAmount, Double maxAmount);

    List<TransactionDTO> getTransactionsByStatus(String status);

    void deleteTransaction(Long id);

     */
}
