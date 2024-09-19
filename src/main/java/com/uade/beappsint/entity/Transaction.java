package com.uade.beappsint.entity;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.transaction.TransactionDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "amount_usd")
    private Double amountUSD;

    @Column(name = "amount_ars")
    private Double amountARS;

    @Column(name = "conversion_rate")
    private Double conversionRate;

    @Column(name = "paid")
    private Boolean paid;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "transaction_product",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public TransactionDTO toDTO() {
        List<ProductDTO> productsDTO = new ArrayList<>();
        for (Product product : products) {
            productsDTO.add(product.toDTO());
        }

        return TransactionDTO.builder()
                .id(id)
                .customerInfo(customer.toDto())
                .date(date)
                .amountARS(amountARS)
                .amountUSD(amountUSD)
                .products(productsDTO)
                .build();
    }
}
