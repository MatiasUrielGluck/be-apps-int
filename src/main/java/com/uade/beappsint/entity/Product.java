package com.uade.beappsint.entity;

import com.uade.beappsint.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "category")
    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "views")
    private int views;

    @Column(name = "year")
    private int year;

    @Column(name = "director")
    private String director;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Customer createdBy;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    public ProductDTO toDTO() {
        return ProductDTO.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .stock(this.stock)
                .price(this.price)
                .category(this.category)
                .imageUrl(this.imageUrl)
                .year(this.year)
                .director(this.director)
                .createdByEmail(this.createdBy.getEmail())
                .build();
    }
}