package com.uade.beappsint.service;

import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResidentialRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.dto.profile.ProfileEditionDTO;

/**
 * Service interface for customer-related operations.
 */
public interface CustomerService {

    /**
     * Performs basic KYC (Know Your Customer) verification.
     *
     * @param kycBasicRequestDTO the request data for basic KYC verification.
     * @return the response data after successful basic KYC verification.
     */
    KycResponseDTO basicKyc(KycBasicRequestDTO kycBasicRequestDTO);

    /**
     * Performs residential KYC (Know Your Customer) verification.
     *
     * @param kycResidentialRequestDTO the request data for residential KYC verification.
     * @return the response data after successful residential KYC verification.
     */
    KycResponseDTO residentialKyc(KycResidentialRequestDTO kycResidentialRequestDTO);

    /**
     * Edits the information of a customer.
     *
     * @param requestDTO the request data for editing customer information.
     * @return the updated customer information.
     */
    CustomerInfoDTO editCustomerInfo(ProfileEditionDTO requestDTO);
}
