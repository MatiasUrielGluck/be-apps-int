package com.uade.beappsint.controller;

import com.uade.beappsint.entity.Cart;
import com.uade.beappsint.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestParam Long userId, @RequestParam Long productId) {
        return ResponseEntity.ok(cartService.addProductToCart(userId, productId));
    }

    @PostMapping("/remove")
    public ResponseEntity<Cart> removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(userId, productId));
    }

    @PostMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.clearCart(userId));
    }

    @PostMapping("/checkout")
    public ResponseEntity<Cart> checkoutCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.checkoutCart(userId));
    }
}