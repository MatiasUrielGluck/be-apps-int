package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.CustomerController;
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
import com.uade.beappsint.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/customer")
@RestController
public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/kyc")
    public ResponseEntity<KycResponseDTO> basicKyc(@RequestBody @Validated KycBasicRequestDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.basicKyc(requestDTO));
    }

    @PostMapping("/kyc-residential")
    public ResponseEntity<KycResponseDTO> residentialKyc(@RequestBody @Validated KycResidentialRequestDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.residentialKyc(requestDTO));
    }

    @PutMapping("/profile")
    public ResponseEntity<CustomerInfoDTO> editCustomerInfo(@RequestBody @Validated ProfileEditionDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.editCustomerInfo(requestDTO));
    }

    @PostMapping("/markAsFavorite/{customerId}")
    public ResponseEntity<String> markProductAsFavorite(@PathVariable @Validated Integer customerId, @RequestBody @Validated ProductDTO productDTO) {
        Long productID = productDTO.getId();
        boolean isAdded = customerService.markProductAsFavorite(customerId, productID);
        if (isAdded) {
            return ResponseEntity.ok("Producto marcado como favorito exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo marcar el producto como favorito.");
        }
    }

    @GetMapping("favorites/{customerId}")
    public ResponseEntity<List<ProductDTO>> getFavoriteProducts(@PathVariable @Validated Integer customerId) {
        List<ProductDTO> favoriteProducts = customerService.getFavoriteProducts(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(favoriteProducts);
    }

    @PostMapping("/{customerId}/admin-request")
    public ResponseEntity<AdminRequestDTO> requestAdminRole(@PathVariable @Validated Integer customerId) {
        AdminRequestDTO adminRequestDTO = customerService.requestAdminRole(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminRequestDTO);
    }

    @PutMapping("/admin/approve/{requestId}")
    public ResponseEntity<AdminRequestDTO> approveAdminRequest(@PathVariable @Validated Integer requestId) {
        AdminRequestDTO adminRequestDTO = customerService.approveAdminRequest(requestId);
        return ResponseEntity.status(HttpStatus.OK).body(adminRequestDTO);
    }

    @GetMapping("/admin/pending-requests")
    public ResponseEntity<List<AdminRequestDTO>> getPendingAdminRequests() {
        List<AdminRequestDTO> pendingRequests = customerService.getPendingAdminRequests();
        return ResponseEntity.status(HttpStatus.OK).body(pendingRequests);
    }

    @PostMapping("addReview/{customerId}")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable @Validated Integer customerId, @RequestBody @Validated ReviewDTO reviewDTO) {
        ReviewDTO createdReview = customerService.addReview(customerId, reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable @Validated Long productId) {
        List<ReviewDTO> reviews = customerService.getReviewsByProductId(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/theme")
    public ResponseEntity<GenericResponseDTO> setUsersTheme(@RequestBody @Validated ThemeDTO themeDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.setUsersTheme(themeDTO));
    }
}
