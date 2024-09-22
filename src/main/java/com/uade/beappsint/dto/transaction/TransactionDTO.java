package com.uade.beappsint.dto.transaction;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object for transactions.
 * Contains the transaction ID, customer information, date, amounts in USD and ARS, and a list of products involved in the transaction.
 */
@Builder
@Data
public class TransactionDTO {
    private Long id;
    private CustomerInfoDTO customerInfo;
    private LocalDate date;
    private Double amountUSD;
    private Double amountARS;
    private List<ProductDTO> products;
}
