package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByCustomerId(Integer customerId);

    
    
    
    
    
    
    
    
    
    /*
    List<Transaction> findByStatus(String status);

    List<Transaction> findByAmountBetween(Double minAmount, Double maxAmount);

    List<Transaction> findByCustomerId(Integer customer_id);

    List<Transaction> findTop10ByOrderByDateDesc();

     */
}
