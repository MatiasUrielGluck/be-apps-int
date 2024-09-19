package com.uade.beappsint.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "transaction_product",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
}
