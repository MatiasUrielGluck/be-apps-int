package com.uade.beappsint.dto.transaction;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class TransactionDTO {
    private CustomerInfoDTO customerInfo;
    private LocalDate date;
    private Double amountUSD;
    private List<ProductDTO> products;
}
