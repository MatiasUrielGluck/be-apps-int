package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.CartController;
import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.entity.Cart;
import com.uade.beappsint.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Implementation of the CartController interface.
 * Provides endpoints for managing the shopping cart.
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {
    private final CartService cartService;

    /**
     * Adds a product to the cart.
     *
     * @param addRequestDTO the request data transfer object containing product details
     * @return a ResponseEntity containing the updated cart
     */
    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestBody AddRequestDTO addRequestDTO) {
        return ResponseEntity.ok(cartService.addProductToCart(addRequestDTO));
    }

    /**
     * Removes a product from the cart.
     *
     * @param productId the ID of the product to remove
     * @return a ResponseEntity containing the updated cart
     */
    @PutMapping("/remove/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(productId));
    }

    /**
     * Clears the cart.
     *
     * @return a ResponseEntity containing the updated cart
     */
    @PutMapping("/clear")
    public ResponseEntity<Cart> clearCart() {
        return ResponseEntity.ok(cartService.clearCart());
    }

    /**
     * Checks out the cart.
     *
     * @return a ResponseEntity containing the checked-out cart
     */
    @PostMapping("/checkout")
    public ResponseEntity<Cart> checkoutCart() {
        return ResponseEntity.ok(cartService.checkoutCart());
    }
}
