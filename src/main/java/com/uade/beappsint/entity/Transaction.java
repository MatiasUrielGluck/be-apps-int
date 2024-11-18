package com.uade.beappsint.entity;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Customer customer;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "amount_usd")
    private Double amountUSD;

    @Column(name = "amount_ars")
    private Double amountARS;

    @Column(name = "conversion_rate")
    private Double conversionRate;

    @Column(name = "customer_email")
    private String customerEmail;

    public TransactionDTO toDTO() {
        return TransactionDTO.builder()
                .id(id)
                .customerInfo(customer.toDto())
                .date(date)
                .amountARS(amountARS)
                .amountUSD(amountUSD)
                .customerEmail(customerEmail)
                .build();
    }
}
