package com.uade.beappsint.dto.cart;

import lombok.Data;

/**
 * Data Transfer Object for adding a product to the cart.
 * Contains the user ID and product ID required for the operation.
 */
@Data
public class AddRequestDTO {
    private Integer amount;
    private Long productId;
}
