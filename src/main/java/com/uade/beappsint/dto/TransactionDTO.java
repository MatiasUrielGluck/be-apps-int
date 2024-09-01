package com.uade.beappsint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TransactionDTO {
    private String cart; 
    private Date date;
}