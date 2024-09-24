package com.uade.beappsint.dto.cart;

import com.uade.beappsint.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing an item in the shopping cart.
 * Contains the item ID, the product details, and the quantity of the product.
 */
@Data
@Builder
public class CartItemDTO {
    private Long id;
    private ProductDTO product;
    private int quantity;
}
