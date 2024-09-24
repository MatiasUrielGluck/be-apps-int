package com.uade.beappsint.repository;

import com.uade.beappsint.entity.TransactionItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionItemRepository extends CrudRepository<TransactionItem, Long> {
    List<TransactionItem> findAllByTransactionId(Long transactionId);
}
