package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import com.uade.beappsint.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionDTO initializeTransaction() {
        return null;
    }

    @Override
    public TransactionDTO confirmTransaction(Long transactionId) {
        return null;
    }
}
