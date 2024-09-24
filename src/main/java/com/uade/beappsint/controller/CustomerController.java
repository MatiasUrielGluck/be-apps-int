package com.uade.beappsint.controller;

import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResidentialRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.dto.profile.ProfileEditionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

/**
 * Interface for customer-related endpoints.
 * Provides methods for handling KYC and profile operations.
 */
@Tag(name = "Customer", description = "Endpoints for customer kyc and profile")
public interface CustomerController {

    /**
     * Submits basic KYC information with mandatory data.
     *
     * @param requestDTO the basic KYC request data transfer object
     * @return a ResponseEntity containing the KYC response data transfer object
     */
    @Operation(summary = "Basic KYC with mandatory data", description = "Saves the kyc basic data in the system")
    ResponseEntity<KycResponseDTO> basicKyc(KycBasicRequestDTO requestDTO);

    /**
     * Submits residential KYC information with mandatory data.
     *
     * @param requestDTO the residential KYC request data transfer object
     * @return a ResponseEntity containing the KYC response data transfer object
     */
    @Operation(summary = "Residential KYC with mandatory data", description = "Saves the kyc residential data in the system")
    ResponseEntity<KycResponseDTO> residentialKyc(KycResidentialRequestDTO requestDTO);

    /**
     * Edits the profile information of the customer.
     *
     * @param requestDTO the profile edition request data transfer object
     * @return a ResponseEntity containing the customer information data transfer object
     */
    @Operation(summary = "Profile info edition", description = "Allows certain profile info to be updated in the system")
    ResponseEntity<CustomerInfoDTO> editCustomerInfo(ProfileEditionDTO requestDTO);
}
