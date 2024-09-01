package com.uade.beappsint.controller;

import com.uade.beappsint.dto.kyc.KycRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/customer")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/kyc")
    public ResponseEntity<KycResponseDTO> basicKyc(@RequestBody @Validated KycRequestDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.basicKyc(requestDTO));
    }
}
