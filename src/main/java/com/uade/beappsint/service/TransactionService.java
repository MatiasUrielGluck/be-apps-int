package com.uade.beappsint.service;

import com.uade.beappsint.dto.transaction.TransactionDTO;

import java.util.List;

/**
 * Service interface for transaction-related operations.
 */
public interface TransactionService {
    TransactionDTO createTransaction();

    List<TransactionDTO> getTransactions();

    TransactionDTO getTransactionById(Long id);
}
