package com.uade.beappsint.dto.cart;

import com.uade.beappsint.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a shopping cart.
 * Contains the cart ID, list of cart items, and the total price of the cart.
 */
@Data
@Builder
public class CartDTO {
    private Long id;
    private List<CartItemDTO> cartItems;
    private double totalPrice;
}
