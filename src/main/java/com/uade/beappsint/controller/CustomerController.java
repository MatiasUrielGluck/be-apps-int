package com.uade.beappsint.controller;

import com.uade.beappsint.dto.kyc.KycRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/customer")
@RestController
public interface CustomerController {
    @PostMapping("/kyc")
    ResponseEntity<KycResponseDTO> basicKyc(@RequestBody @Validated KycRequestDTO requestDTO);
}
