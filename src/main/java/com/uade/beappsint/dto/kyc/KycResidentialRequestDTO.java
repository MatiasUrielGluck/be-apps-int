package com.uade.beappsint.dto.kyc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

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
