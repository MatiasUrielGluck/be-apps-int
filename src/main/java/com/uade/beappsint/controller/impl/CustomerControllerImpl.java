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

@RequestMapping("/customer")
@RestController
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/kyc")
    public ResponseEntity<KycResponseDTO> basicKyc(@RequestBody @Validated KycBasicRequestDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.basicKyc(requestDTO));
    }

    @PostMapping("/kyc-residential")
    public ResponseEntity<KycResponseDTO> residentialKyc(@RequestBody @Validated KycResidentialRequestDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.residentialKyc(requestDTO));
    }

    @PutMapping("/profile")
    public ResponseEntity<CustomerInfoDTO> editCustomerInfo(@RequestBody @Validated ProfileEditionDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.editCustomerInfo(requestDTO));
    }
}
