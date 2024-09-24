package com.uade.beappsint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private String createdByEmail;
}

