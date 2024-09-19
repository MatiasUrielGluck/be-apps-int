package com.uade.beappsint.service;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    TransactionDTO initializeTransaction();

    TransactionDTO confirmTransaction(Long transactionId);
}
