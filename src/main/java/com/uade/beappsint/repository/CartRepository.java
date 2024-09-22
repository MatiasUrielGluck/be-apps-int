package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Cart entities.
 * Extends CrudRepository to provide CRUD operations for Cart entities.
 */
@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<Cart> findByCustomerId(Integer customerId);
}
