package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.uade.beappsint.entity.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query("SELECT c.favoriteProducts FROM Customer c WHERE c.id = :customerId")
    List<Product> findFavoriteProductsByCustomerId(Integer customerId);
}
