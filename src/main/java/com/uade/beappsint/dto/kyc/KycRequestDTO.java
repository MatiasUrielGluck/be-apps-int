package com.uade.beappsint.dto.kyc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class KycRequestDTO {
    @NotNull @NotBlank
    private String firstname;

    @NotNull @NotBlank
    private String lastname;

    @NotNull
    private LocalDate dateOfBirth;
}
