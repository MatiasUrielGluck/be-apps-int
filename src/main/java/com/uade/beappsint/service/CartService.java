package com.uade.beappsint.service;

import com.uade.beappsint.entity.Cart;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    Cart addProductToCart(Long userId, Long productId);

    Cart removeProductFromCart(Long userId, Long productId);

    Cart clearCart(Long userId);

    Cart checkoutCart(Long userId);
}
