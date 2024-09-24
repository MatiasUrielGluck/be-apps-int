package com.uade.beappsint.entity;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entity representing a transaction.
 * Contains transaction details such as ID, customer, date, amounts in USD and ARS, conversion rate, payment status, and products.
 */
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

    /**
     * Converts the transaction entity to a TransactionDTO.
     *
     * @return a TransactionDTO containing the transaction's information.
     */
    public TransactionDTO toDTO() {
        return TransactionDTO.builder()
                .id(id)
                .customerInfo(customer.toDto())
                .date(date)
                .amountARS(amountARS)
                .amountUSD(amountUSD)
                .build();
    }
}
