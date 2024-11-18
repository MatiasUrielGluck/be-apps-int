package com.uade.beappsint.dto.transaction;

import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class TransactionDTO {
    private Long id;
    private CustomerInfoDTO customerInfo;
    private LocalDate date;
    private Double amountUSD;
    private Double amountARS;
    private Double conversionRate;
    private String customerEmail;
    private List<TransactionItemDTO> items;
}
