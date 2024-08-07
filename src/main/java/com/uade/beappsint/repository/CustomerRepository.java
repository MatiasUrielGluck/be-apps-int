package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    Boolean existsByEmail(String email);
}
