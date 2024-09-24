package com.uade.beappsint.entity;

import com.uade.beappsint.dto.ImageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ImageDTO toDTO() {
        return ImageDTO.builder()
                .id(this.id)
                .url(this.url)
                .productId(this.product.getId())
                .build();
    }

}