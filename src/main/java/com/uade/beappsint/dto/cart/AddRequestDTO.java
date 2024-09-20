package com.uade.beappsint.dto.cart;

import lombok.Data;

@Data
public class AddRequestDTO {
    private Integer userId;
    private Long productId;
}
