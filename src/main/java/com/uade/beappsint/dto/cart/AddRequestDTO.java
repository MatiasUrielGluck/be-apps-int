package com.uade.beappsint.dto.cart;

import lombok.Data;

@Data
public class AddRequestDTO {
    private Integer amount;
    private Long productId;
}
