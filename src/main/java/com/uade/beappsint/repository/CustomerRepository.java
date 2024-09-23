package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.uade.beappsint.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    Boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO customer_favorite_products (customer_id, product_id) VALUES (:customerId, :productId)", nativeQuery = true)
    int addFavoriteProduct(@Param("customerId") Integer customerId, @Param("productId") Long productId);

    @Query("SELECT c.favoriteProducts FROM Customer c WHERE c.id = :customerId")
    List<Product> findFavoriteProductsByCustomerId(Integer customerId);
}
