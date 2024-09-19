package com.uade.beappsint.controller;

import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.entity.Cart;
import org.springframework.http.ResponseEntity;

public interface CartController {
    ResponseEntity<Cart> addProductToCart(AddRequestDTO addRequestDTO);

    ResponseEntity<Cart> removeProductFromCart(Long productId);

    ResponseEntity<Cart> clearCart();

    ResponseEntity<Cart> checkoutCart();
}
