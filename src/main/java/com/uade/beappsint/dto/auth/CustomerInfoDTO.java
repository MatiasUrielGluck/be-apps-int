package com.uade.beappsint.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerInfoDTO {
    private Integer id;
    private String email;
    private String  firstname;
    private String  lastname;
    private LocalDate dateOfBirth;
    private Boolean isAdmin;
}
