package com.uade.beappsint.dto.cart;

import com.uade.beappsint.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDTO {
    private Long id;
    private ProductDTO product;
    private int quantity;
}
