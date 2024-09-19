package com.uade.beappsint.controller;

import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.entity.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public interface CartController {
    @PostMapping("/add")
    ResponseEntity<Cart> addProductToCart(@RequestBody AddRequestDTO addRequestDTO);

    @PutMapping("/remove/{productId}")
    ResponseEntity<Cart> removeProductFromCart(@PathVariable Long productId);

    @PutMapping("/clear")
    ResponseEntity<Cart> clearCart();

    @PostMapping("/checkout")
    ResponseEntity<Cart> checkoutCart();
}
