package com.uade.beappsint.service;

import com.uade.beappsint.dto.kyc.KycRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;

public interface CustomerService {
    KycResponseDTO basicKyc(KycRequestDTO kycRequestDTO);
}
