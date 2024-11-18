package com.uade.beappsint.service.impl;

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
import com.uade.beappsint.entity.*;
import com.uade.beappsint.enums.KycStatusEnum;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.exception.ResourceNotFoundException;
import com.uade.beappsint.repository.AdminRequestRepository;
import com.uade.beappsint.repository.CartRepository;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.repository.ReviewRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.CustomerService;
import com.uade.beappsint.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ProductService productService;
    private final AuthService authService;
    private final AdminRequestRepository adminRequestRepository;
    private final ReviewRepository reviewRepository;
    private final CartRepository cartRepository;

    public KycResponseDTO basicKyc(KycBasicRequestDTO kycBasicRequestDTO) {
        Customer customer = authService.getAuthenticatedCustomer();
        if (!customer.getKycStatus().equals(KycStatusEnum.BASIC_KYC))
            throw new BadRequestException("Kyc stage already completed.");

        int minAge = 18;
        boolean validDateOfBirth = kycBasicRequestDTO.getDateOfBirth() != null && Period.between(kycBasicRequestDTO.getDateOfBirth(), LocalDate.now()).getYears() >= minAge;
        if (!validDateOfBirth) throw new BadRequestException("Customer must be at least " + minAge + " years old.");
        customer.setFirstname(kycBasicRequestDTO.getFirstname());
        customer.setLastname(kycBasicRequestDTO.getLastname());
        customer.setDateOfBirth(kycBasicRequestDTO.getDateOfBirth());
        customer.setKycStatus(KycStatusEnum.RESIDENTIAL_KYC);
        customerRepository.save(customer);

        return KycResponseDTO.builder()
                .kycStatus(KycStatusEnum.RESIDENTIAL_KYC)
                .build();
    }

    public KycResponseDTO residentialKyc(KycResidentialRequestDTO kycResidentialRequestDTO) {
        Customer customer = authService.getAuthenticatedCustomer();
        if (!customer.getKycStatus().equals(KycStatusEnum.RESIDENTIAL_KYC))
            throw new BadRequestException("Kyc stage not accessible.");

        customer.setStreetName(kycResidentialRequestDTO.getStreetName());
        customer.setStreetNumber(kycResidentialRequestDTO.getStreetNumber());
        customer.setComplementaryAddress(kycResidentialRequestDTO.getComplementaryAddress() != null ? kycResidentialRequestDTO.getComplementaryAddress() : "N/A");
        customer.setPhoneNumber(kycResidentialRequestDTO.getPhoneNumber());
        customer.setKycStatus(KycStatusEnum.COMPLETED_KYC);
        customerRepository.save(customer);

        return KycResponseDTO.builder()
                .kycStatus(KycStatusEnum.COMPLETED_KYC)
                .build();
    }

    public CustomerInfoDTO editCustomerInfo(ProfileEditionDTO requestDTO) {
        Customer customer = authService.getAuthenticatedCustomer();
        customer.setStreetName(requestDTO.getStreetName() == null ? customer.getStreetName() : requestDTO.getStreetName());
        customer.setStreetNumber(requestDTO.getStreetNumber() == null ? customer.getStreetNumber() : requestDTO.getStreetNumber());
        customer.setComplementaryAddress(requestDTO.getComplementaryAddress() == null ? customer.getComplementaryAddress() : requestDTO.getComplementaryAddress());
        customer.setPhoneNumber(requestDTO.getPhoneNumber() == null ? customer.getPhoneNumber() : requestDTO.getPhoneNumber());
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.toDto();
    }

    public boolean markProductAsFavorite(Integer customerId, Long productId) {
        Customer customerAuth = authService.getAuthenticatedCustomer();
        if (!customerAuth.getKycStatus().equals(KycStatusEnum.BASIC_KYC))
            throw new BadRequestException("Kyc stage already completed.");
        int result = customerRepository.addFavoriteProduct(customerId, productId);
        return result > 0;
    }

    public List<ProductDTO> getFavoriteProducts(Integer customerId) {
        Customer customerAuth = authService.getAuthenticatedCustomer();
        if (!customerAuth.getKycStatus().equals(KycStatusEnum.BASIC_KYC))
            throw new BadRequestException("Kyc stage already completed.");
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return customerRepository.findFavoriteProductsByCustomerId(customer.getId()).stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public AdminRequestDTO requestAdminRole(Integer customerId) {
        Customer customerAuth = authService.getAuthenticatedCustomer();
        if (!customerAuth.getKycStatus().equals(KycStatusEnum.BASIC_KYC))
            throw new BadRequestException("Kyc stage already completed.");

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setCustomer(customer);
        adminRequest.setStatus(false);
        adminRequest.setRequestDate(java.time.LocalDate.now());
        adminRequest = adminRequestRepository.save(adminRequest);
        return adminRequest.toDTO();
    }

    public AdminRequestDTO approveAdminRequest(Integer requestId) {
        assertAdmin();
        if (requestId == null) {
            throw new IllegalArgumentException("El ID de la solicitud de administrador no debe ser nulo");
        }
        AdminRequest adminRequest = adminRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin request not found"));
        adminRequest.setApproved(true);
        adminRequest.setApprovalDate(java.time.LocalDate.now());
        adminRequest = adminRequestRepository.save(adminRequest);
        Customer customer = adminRequest.getCustomer();
        customer.setAdmin(true);
        customerRepository.save(customer);
        return adminRequest.toDTO();
    }

    public ReviewDTO addReview(Integer customerId, ReviewDTO reviewDTO) {
        Customer customerAuth = authService.getAuthenticatedCustomer();
        if (!customerAuth.getKycStatus().equals(KycStatusEnum.BASIC_KYC))
            throw new BadRequestException("Kyc stage already completed.");
        Review review = new Review();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        ProductDTO productDTO = productService.getProductById(reviewDTO.getProductId());
        Product product = new Product();
            product.setId(productDTO.getId());
            product.setCategory(productDTO.getCategory());
            product.setName(productDTO.getName());
            product.setDirector(productDTO.getDirector());
            product.setPrice(productDTO.getPrice());
            product.setYear(productDTO.getYear());
        review.setCustomer(customer);
        review.setProduct(product);
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        Review savedReview = reviewRepository.save(review);
        return savedReview.toDTO();
    }

    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        Customer customerAuth = authService.getAuthenticatedCustomer();
        if (!customerAuth.getKycStatus().equals(KycStatusEnum.BASIC_KYC))
            throw new BadRequestException("Kyc stage already completed.");
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream().map(Review::toDTO).collect(Collectors.toList());
    }

    public List<AdminRequestDTO> getPendingAdminRequests() {
        Customer customerAuth = authService.getAuthenticatedCustomer();
        if (!customerAuth.getKycStatus().equals(KycStatusEnum.BASIC_KYC))
            throw new BadRequestException("Kyc stage already completed.");
        assertAdmin();
        return adminRequestRepository.findByApprovedFalse().stream().map(AdminRequest::toDTO).collect(Collectors.toList());
    }

    public GenericResponseDTO setUsersTheme(ThemeDTO themeDTO) {
        Customer customer = authService.getAuthenticatedCustomer();
        customer.setTheme(themeDTO.getTheme());
        customerRepository.save(customer);

        return GenericResponseDTO.builder()
                .message("OK")
                .build();
    }

    public List<CustomerInfoDTO> getRegisteredUsers() {
        Customer authed = authService.getAuthenticatedCustomer();
        if (!authed.isAdmin()) throw new BadRequestException("Only admins can access this resource!");

        List<Customer> registeredCustomers = (List<Customer>) customerRepository.findAll();

        List<CustomerInfoDTO> customerInfoDTOS = new ArrayList<>();
        for (Customer customer : registeredCustomers) {
            if (!customer.getIsEnabled()) continue;
            customerInfoDTOS.add(customer.toDto());
        }

        return customerInfoDTOS;
    }

    public GenericResponseDTO deleteUser(Integer id) {
        Customer authed = authService.getAuthenticatedCustomer();
        if (!authed.isAdmin()) throw new BadRequestException("Only admins can access this resource!");

        if (authed.getId().equals(id)) throw new BadRequestException("You are not allowed to delete yourself...");

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        cartRepository.findByCustomerId(customer.getId()).ifPresent(customerCart -> cartRepository.deleteById(customerCart.getId()));
        customerRepository.deleteById(id);
        // customer.setIsEnabled(false);
        // customerRepository.save(customer);

        return GenericResponseDTO.builder()
                .message("User deleted successfully")
                .build();
    }

    public GenericResponseDTO toggleAdmin(Integer id) {
        Customer authed = authService.getAuthenticatedCustomer();
        if (!authed.isAdmin()) throw new BadRequestException("Only admins can access this resource!");

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customer.setAdmin(!customer.isAdmin());
        customerRepository.save(customer);

        return GenericResponseDTO.builder()
                .message("User admin privileges toggled. Current status: " + customer.isAdmin())
                .build();
    }

    private void assertAdmin() {
        Customer customer = authService.getAuthenticatedCustomer();
        if (!customer.isAdmin()) {
            throw new RuntimeException("Access denied: only administrators can perform this action.");
        }
    }

}
