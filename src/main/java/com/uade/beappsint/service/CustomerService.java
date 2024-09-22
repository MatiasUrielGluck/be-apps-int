package com.uade.beappsint.service;

import com.uade.beappsint.dto.AdminRequestDTO;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.dto.ReviewDTO;
import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResidentialRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.dto.profile.ProfileEditionDTO;

import java.util.List;

public interface CustomerService {
    KycResponseDTO basicKyc(KycBasicRequestDTO kycBasicRequestDTO);

    KycResponseDTO residentialKyc(KycResidentialRequestDTO kycResidentialRequestDTO);

    CustomerInfoDTO editCustomerInfo(ProfileEditionDTO requestDTO);

    List<ProductDTO> getFavoriteProducts(Integer customerId);

    AdminRequestDTO requestAdminRole(Integer customerId);

    AdminRequestDTO approveAdminRequest(Integer requestId);

    ReviewDTO addReview(Integer customerId, ReviewDTO reviewDTO);

    List<ReviewDTO> getReviewsByProductId(Long productId);
}
