package com.uade.beappsint.controller;

import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.dto.cart.CartDTO;
import org.springframework.http.ResponseEntity;

/**
 * Controller interface for cart-related operations.
 */
public interface CartController {
    ResponseEntity<CartDTO> addProductToCart(AddRequestDTO addRequestDTO);

    ResponseEntity<CartDTO> removeProductFromCart(Long productId);

    ResponseEntity<CartDTO> removeOneProductFromCart(Long productId);

    ResponseEntity<CartDTO> clearCart();

    ResponseEntity<CartDTO> checkoutCart();

    ResponseEntity<CartDTO> getCart();
}
