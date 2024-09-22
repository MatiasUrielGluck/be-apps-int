package com.uade.beappsint.entity;

import com.uade.beappsint.dto.cart.CartItemDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    private int quantity;

    public CartItemDTO toDTO() {
        return CartItemDTO.builder()
                .id(this.id)
                .product(this.product.toDTO())
                .quantity(this.quantity)
                .build();
    }
}
