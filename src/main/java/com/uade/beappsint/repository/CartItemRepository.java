package com.uade.beappsint.repository;

import com.uade.beappsint.entity.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);
}
