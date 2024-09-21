package com.uade.beappsint.service;

import com.uade.beappsint.dto.AdminRequestDTO;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResidentialRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.dto.profile.ProfileEditionDTO;
import com.uade.beappsint.entity.AdminRequest;
import com.uade.beappsint.entity.Product;

import java.util.List;

public interface CustomerService {
    KycResponseDTO basicKyc(KycBasicRequestDTO kycBasicRequestDTO);

    KycResponseDTO residentialKyc(KycResidentialRequestDTO kycResidentialRequestDTO);

    CustomerInfoDTO editCustomerInfo(ProfileEditionDTO requestDTO);

    // Obtener productos favoritos
    List<ProductDTO> getFavoriteProducts(Integer customerId);

    // Crear una solicitud de administrador
    AdminRequestDTO requestAdminRole(Integer customerId);

    // Aprobar una solicitud de administrador
    AdminRequestDTO approveAdminRequest(Integer requestId);
}
