package com.uade.beappsint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TransactionDTO {
    private String cart; // Cambiar según la implementación de la clase Cart
    private Date date;
}