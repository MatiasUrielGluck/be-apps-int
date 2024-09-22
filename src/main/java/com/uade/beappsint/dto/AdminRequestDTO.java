package com.uade.beappsint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {
    private Integer id;
    private Integer customerId;
    private boolean approved;
    private String requestDate;
    private String approvalDate;
}