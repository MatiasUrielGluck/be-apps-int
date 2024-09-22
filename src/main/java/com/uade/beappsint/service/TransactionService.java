package com.uade.beappsint.service;

import com.uade.beappsint.dto.transaction.TransactionDTO;

/**
 * Service interface for transaction-related operations.
 */
public interface TransactionService {

    /**
     * Initializes a new transaction.
     *
     * @return the initialized transaction data.
     */
    TransactionDTO initializeTransaction();

    /**
     * Confirms a transaction by its ID.
     *
     * @param transactionId the ID of the transaction to confirm.
     * @return the confirmed transaction data.
     */
    TransactionDTO confirmTransaction(Long transactionId);
}
