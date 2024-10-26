package com.uade.beappsint.dto.favs;

import lombok.Data;

@Data
public class FavoriteDTO {
    private Long id;
    private Long productId;
    private Long userId;
}
