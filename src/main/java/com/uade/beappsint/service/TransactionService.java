package com.uade.beappsint.service;

import com.uade.beappsint.dto.transaction.TransactionDTO;

public interface TransactionService {
    TransactionDTO initializeTransaction();

    TransactionDTO confirmTransaction(Long transactionId);
}
