package com.uade.beappsint.entity;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.dto.cart.CartDTO;
import com.uade.beappsint.dto.cart.CartItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    public CartDTO toDTO() {
        int quantity = 0;
        List<CartItemDTO> mappedCartItems = new ArrayList<>();
        for (CartItem cartItem: cartItems) {
            mappedCartItems.add(cartItem.toDTO());
            quantity += cartItem.getQuantity();
        }

        return CartDTO.builder()
                .id(this.id)
                .cartItems(mappedCartItems)
                .totalPrice(this.totalPrice)
                .quantity(quantity)
                .build();
    }
}
