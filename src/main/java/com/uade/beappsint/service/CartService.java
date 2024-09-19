package com.uade.beappsint.service;

import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.entity.Cart;

public interface CartService {
    Cart addProductToCart(AddRequestDTO addRequestDTO);

    Cart removeProductFromCart(Long productId);

    Cart clearCart();

    Cart checkoutCart();

    Cart getUserCart();
}
