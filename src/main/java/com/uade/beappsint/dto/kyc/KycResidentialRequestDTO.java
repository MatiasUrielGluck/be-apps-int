package com.uade.beappsint.dto.kyc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for KYC (Know Your Customer) residential requests.
 * Contains the street name, street number, complementary address, and phone number required for the KYC process.
 */
@Data
@Builder
public class KycResidentialRequestDTO {
    @NotNull @NotBlank
    private String streetName;

    @NotNull @NotBlank
    private String streetNumber;

    private String complementaryAddress; // Floor, department number, if applicable

    @NotNull @NotBlank
    private String phoneNumber;
}
