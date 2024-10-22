package com.uade.beappsint.dto.cart;

import com.uade.beappsint.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDTO {
    private Long id;
    private List<CartItemDTO> cartItems;
    private double totalPrice;
    private int quantity;
}
