package com.uade.beappsint.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private int stock;
    private double price;
    private String category;
    private String imageUrl;
    private int year;
    private String director;
}

