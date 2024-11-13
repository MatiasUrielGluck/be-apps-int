package com.uade.beappsint.entity;

import com.uade.beappsint.dto.transaction.TransactionItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "transaction_item")
public class TransactionItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;

    private int quantity;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;


    @Column(name = "amount_unit_usd")
    private double amountUnitUSD;

    @Column(name = "amount_unit_ars")
    private double amountUnitARS;

    public TransactionItemDTO toDTO() {
        return TransactionItemDTO.builder()
                .id(this.id)
                .transactionId(this.transaction.getId())
                .quantity(this.quantity)
                .productId(this.productId)
                .productName(this.productName)
                .amountUnitUSD(this.amountUnitUSD)
                .amountUnitARS(this.amountUnitARS)
                .build();
    }
}
