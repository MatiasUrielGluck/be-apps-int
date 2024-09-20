package com.uade.beappsint.controller;

import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResidentialRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.dto.profile.ProfileEditionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Customer", description = "Endpoints for customer kyc and profile")
public interface CustomerController {
    @Operation(summary = "Basic KYC with mandatory data", description = "Saves the kyc basic data in the system")
    ResponseEntity<KycResponseDTO> basicKyc(KycBasicRequestDTO requestDTO);

    @Operation(summary = "Residential KYC with mandatory data", description = "Saves the kyc residential data in the system")
    ResponseEntity<KycResponseDTO> residentialKyc(KycResidentialRequestDTO requestDTO);

    @Operation(summary = "Profile info edition", description = "Allows certain profile info to be updated in the system")
    ResponseEntity<CustomerInfoDTO> editCustomerInfo(ProfileEditionDTO requestDTO);
}
