package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.CustomerController;
import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResidentialRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.dto.profile.ProfileEditionDTO;
import com.uade.beappsint.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Implementation of the CustomerController interface.
 * Provides endpoints for handling KYC and profile operations.
 */
@RequestMapping("/customer")
@RestController
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    /**
     * Constructs a new CustomerControllerImpl with the specified CustomerService.
     *
     * @param customerService the customer service to use
     */
    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Submits basic KYC information with mandatory data.
     *
     * @param requestDTO the basic KYC request data transfer object
     * @return a ResponseEntity containing the KYC response data transfer object
     */
    @PostMapping("/kyc")
    public ResponseEntity<KycResponseDTO> basicKyc(@RequestBody @Validated KycBasicRequestDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.basicKyc(requestDTO));
    }

    /**
     * Submits residential KYC information with mandatory data.
     *
     * @param requestDTO the residential KYC request data transfer object
     * @return a ResponseEntity containing the KYC response data transfer object
     */
    @PostMapping("/kyc-residential")
    public ResponseEntity<KycResponseDTO> residentialKyc(@RequestBody @Validated KycResidentialRequestDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.residentialKyc(requestDTO));
    }

    /**
     * Edits the profile information of the customer.
     *
     * @param requestDTO the profile edition request data transfer object
     * @return a ResponseEntity containing the customer information data transfer object
     */
    @PutMapping("/profile")
    public ResponseEntity<CustomerInfoDTO> editCustomerInfo(@RequestBody @Validated ProfileEditionDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.editCustomerInfo(requestDTO));
    }
}
