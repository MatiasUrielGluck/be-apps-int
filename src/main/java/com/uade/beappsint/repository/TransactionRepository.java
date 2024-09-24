package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Transaction entities.
 * Extends CrudRepository to provide CRUD operations for Transaction entities.
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByCustomerId(Integer customerId);
}
