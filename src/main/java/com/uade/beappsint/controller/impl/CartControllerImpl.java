package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.CartController;
import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.entity.Cart;
import com.uade.beappsint.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestBody AddRequestDTO addRequestDTO) {
        return ResponseEntity.ok(cartService.addProductToCart(addRequestDTO));
    }

    @PutMapping("/remove/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(productId));
    }

    @PutMapping("/clear")
    public ResponseEntity<Cart> clearCart() {
        return ResponseEntity.ok(cartService.clearCart());
    }

    @PostMapping("/checkout")
    public ResponseEntity<Cart> checkoutCart() {
        return ResponseEntity.ok(cartService.checkoutCart());
    }
}
