package com.uade.beappsint.controller;

import com.uade.beappsint.dto.kyc.KycRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import org.springframework.http.ResponseEntity;

public interface CustomerController {
    ResponseEntity<KycResponseDTO> basicKyc(KycRequestDTO requestDTO);
}
