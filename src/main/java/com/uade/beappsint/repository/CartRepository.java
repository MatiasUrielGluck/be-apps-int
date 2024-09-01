package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<List<Cart>> findByUserId(Long userId);
}