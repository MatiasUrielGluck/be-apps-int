package com.uade.beappsint.service;

import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResidentialRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.dto.profile.ProfileEditionDTO;

public interface CustomerService {
    KycResponseDTO basicKyc(KycBasicRequestDTO kycBasicRequestDTO);

    KycResponseDTO residentialKyc(KycResidentialRequestDTO kycResidentialRequestDTO);

    CustomerInfoDTO editCustomerInfo(ProfileEditionDTO requestDTO);
}
