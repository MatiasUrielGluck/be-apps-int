package com.uade.beappsint.dto.transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionItemDTO {
    private Long id;
    private Long transactionId;
    private int quantity;
    private Long productId;
    private String productName;
    private double amountUnitUSD;
    private double amountUnitARS;
}
