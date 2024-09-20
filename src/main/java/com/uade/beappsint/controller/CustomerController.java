package com.uade.beappsint.controller;

import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResidentialRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import org.springframework.http.ResponseEntity;

public interface CustomerController {
    ResponseEntity<KycResponseDTO> basicKyc(KycBasicRequestDTO requestDTO);

    ResponseEntity<KycResponseDTO> residentialKyc(KycResidentialRequestDTO requestDTO);
}
