package com.uade.beappsint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerProfileDTO {
    private String name;
    private String lastName;
    private String email;
}