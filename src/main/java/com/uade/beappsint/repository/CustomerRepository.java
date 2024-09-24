package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Customer entities.
 * Extends CrudRepository to provide CRUD operations for Customer entities.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    /**
     * Finds a Customer by their email.
     *
     * @param email the email of the customer.
     * @return an Optional containing the found Customer, or an empty Optional if no Customer is found.
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Checks if a Customer exists by their email.
     *
     * @param email the email of the customer.
     * @return true if a Customer with the given email exists, false otherwise.
     */
    Boolean existsByEmail(String email);
}
