package com.uade.beappsint.service;

import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.dto.cart.CartDTO;
import com.uade.beappsint.entity.Cart;

public interface CartService {
    CartDTO addProductToCart(AddRequestDTO addRequestDTO);

    CartDTO removeProductFromCart(Long productId);

    CartDTO clearCart();

    CartDTO checkoutCart();

    CartDTO getUserCart();
}
