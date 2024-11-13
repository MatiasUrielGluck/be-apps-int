package com.uade.beappsint.dto.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequestDTO {
    private String name;
    private String description;
    private Integer stock;
    private Double price;
    private String category;
    private String imageUrl;
    private Integer year;
    private String director;
}
