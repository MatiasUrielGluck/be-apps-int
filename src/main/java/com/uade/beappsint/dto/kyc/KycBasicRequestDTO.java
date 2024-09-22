package com.uade.beappsint.dto.kyc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object for KYC (Know Your Customer) basic requests.
 * Contains the first name, last name, and date of birth required for the KYC process.
 */
@Data
@Builder
public class KycBasicRequestDTO {
    @NotNull @NotBlank
    private String firstname;

    @NotNull @NotBlank
    private String lastname;

    @NotNull
    private LocalDate dateOfBirth;
}
