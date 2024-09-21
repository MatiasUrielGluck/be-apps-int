package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.AdminRequestDTO;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResidentialRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.dto.profile.ProfileEditionDTO;
import com.uade.beappsint.entity.AdminRequest;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.enums.KycStatusEnum;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.AdminRequestRepository;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AdminRequestRepository adminRequestRepository;
    private final AuthService authService;

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

    public List<ProductDTO> getFavoriteProducts(Integer customerId) {
        // Lógica para obtener productos favoritos
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return customer.getFavoriteProducts().stream()
                .map(this::convertToDTO) // Método para convertir a DTO
                .collect(Collectors.toList());
    }

    public AdminRequestDTO requestAdminRole(Integer customerId) {
        // Lógica para crear solicitud de administrador
        // Implementar según tus necesidades
    }

    public AdminRequestDTO approveAdminRequest(Integer requestId) {
        // Lógica para aprobar solicitud de administrador
        // Implementar según tus necesidades
    }

    private ProductDTO convertToDTO(Product product) {
        // Lógica para convertir un producto a DTO
        return new ProductDTO(/* asignar campos aquí */);
    }

    private AdminRequestDTO convertToAdminRequestDTO(AdminRequest adminRequest) {
        // Lógica para convertir solicitud de administrador a DTO
        return new AdminRequestDTO(/* asignar campos aquí */);
    }
}