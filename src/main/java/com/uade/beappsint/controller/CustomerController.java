package com.uade.beappsint.controller;

import com.uade.beappsint.dto.AdminRequestDTO;
import com.uade.beappsint.dto.GenericResponseDTO;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.dto.ReviewDTO;
import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResidentialRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.dto.profile.ProfileEditionDTO;
import com.uade.beappsint.dto.profile.ThemeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Customer", description = "Endpoints for customer kyc and profile")
public interface CustomerController {
    @Operation(summary = "Basic KYC with mandatory data", description = "Saves the kyc basic data in the system")
    ResponseEntity<KycResponseDTO> basicKyc(KycBasicRequestDTO requestDTO);

    @Operation(summary = "Residential KYC with mandatory data", description = "Saves the kyc residential data in the system")
    ResponseEntity<KycResponseDTO> residentialKyc(KycResidentialRequestDTO requestDTO);

    @Operation(summary = "Profile info edition", description = "Allows certain profile info to be updated in the system")
    ResponseEntity<CustomerInfoDTO> editCustomerInfo(ProfileEditionDTO requestDTO);

    @Operation(summary = "Mark product as favorite", description = "Marks a product as favorite for a customer")
    ResponseEntity<String> markProductAsFavorite(Integer customerId, ProductDTO productDTO);

    @Operation(summary = "Get favorite products", description = "Retrieves the favorite products of a customer")
    ResponseEntity<List<ProductDTO>> getFavoriteProducts(Integer customerId);

    @Operation(summary = "Request admin role", description = "Creates a request for a customer to become an admin")
    ResponseEntity<AdminRequestDTO> requestAdminRole(Integer customerId);

    @Operation(summary = "Approve admin request", description = "Allows an admin to approve a customer admin request")
    ResponseEntity<AdminRequestDTO> approveAdminRequest(Integer requestId);

    @Operation(summary = "Get pending admin requests", description = "Retrieves a list of pending admin requests")
    ResponseEntity<List<AdminRequestDTO>> getPendingAdminRequests();

    @Operation(summary = "Add a review", description = "Creates a new review for a customer.")
    ResponseEntity<ReviewDTO> addReview(Integer customerId, ReviewDTO reviewDTO);

    @Operation(summary = "Get reviews by product", description = "Retrieves all reviews for a specific product.")
    ResponseEntity<List<ReviewDTO>> getReviewsByProductId(Long productId);

    @Operation(summary = "Sets the user's preferred theme", description = "Sets the user's preferred theme to be light or dark.")
    ResponseEntity<GenericResponseDTO> setUsersTheme(ThemeDTO themeDTO);
}
