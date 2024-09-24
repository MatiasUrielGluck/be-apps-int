package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.CartController;
import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.dto.cart.CartDTO;
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
    public ResponseEntity<CartDTO> addProductToCart(@RequestBody AddRequestDTO addRequestDTO) {
        return ResponseEntity.ok(cartService.addProductToCart(addRequestDTO));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartDTO> removeProductFromCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(productId));
    }

    @DeleteMapping("/reduce/{productId}")
    public ResponseEntity<CartDTO> removeOneProductFromCart(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeOneProductFromCart(productId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartDTO> clearCart() {
        return ResponseEntity.ok(cartService.clearCart());
    }

    @GetMapping()
    public ResponseEntity<CartDTO> getCart() {
        return ResponseEntity.ok(cartService.getUserCart());
    }
}
