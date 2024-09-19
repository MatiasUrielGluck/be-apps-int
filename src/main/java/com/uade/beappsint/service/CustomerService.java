package com.uade.beappsint.service;

import com.uade.beappsint.dto.kyc.KycRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    KycResponseDTO basicKyc(KycRequestDTO kycRequestDTO);
}
