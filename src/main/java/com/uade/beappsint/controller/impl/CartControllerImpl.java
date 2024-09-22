package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.CartController;
import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.dto.cart.CartDTO;
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
    public ResponseEntity<CartDTO> addProductToCart(@RequestBody AddRequestDTO addRequestDTO) {
        return ResponseEntity.ok(cartService.addProductToCart(addRequestDTO));
    }

    /**
     * Removes a product from the cart by its ID.
     *
     * @param productId the ID of the product to be removed
     * @return a ResponseEntity containing the updated cart
     */
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartDTO> removeProductFromCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(productId));
    }

    /**
     * Reduces the quantity of a product in the cart by one.
     *
     * @param productId the ID of the product to be reduced
     * @return a ResponseEntity containing the updated cart
     */
    @DeleteMapping("/reduce/{productId}")
    public ResponseEntity<CartDTO> removeOneProductFromCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeOneProductFromCart(productId));
    }

    /**
     * Clears all products from the cart.
     *
     * @return a ResponseEntity containing the updated cart
     */
    @DeleteMapping("/clear")
    public ResponseEntity<CartDTO> clearCart() {
        return ResponseEntity.ok(cartService.clearCart());
    }

    /**
     * Checks out the cart, finalizing the purchase.
     *
     * @return a ResponseEntity containing the updated cart
     */
    @PostMapping("/checkout")
    public ResponseEntity<CartDTO> checkoutCart() {
        return ResponseEntity.ok(cartService.checkoutCart());
    }

    /**
     * Retrieves the current user's cart.
     *
     * @return a ResponseEntity containing the user's cart
     */
    @GetMapping()
    public ResponseEntity<CartDTO> getCart() {
        return ResponseEntity.ok(cartService.getUserCart());
    }
}
