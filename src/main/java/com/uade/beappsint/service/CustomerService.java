package com.uade.beappsint.service;

import com.uade.beappsint.dto.CustomerProfileDTO;
import com.uade.beappsint.dto.TransactionDTO;
import com.uade.beappsint.dto.kyc.KycRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.entity.*;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.CartRepository;
import com.uade.beappsint.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthService authService;
    private final CartRepository cartRepository;

    public CustomerService(CustomerRepository customerRepository, AuthService authService, CartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.authService = authService;
        this.cartRepository = cartRepository;
    }

    public KycResponseDTO basicKyc(KycRequestDTO kycRequestDTO) {
        Customer customer = authService.getAuthenticatedCustomer();
        if (customer.isKycCompleted()) throw new BadRequestException("Kyc already completed.");

        int minAge = 18;
        boolean validDateOfBirth = kycRequestDTO.getDateOfBirth() != null && Period.between(kycRequestDTO.getDateOfBirth(), LocalDate.now()).getYears() >= minAge;
        if (!validDateOfBirth) throw new BadRequestException("Customer must be at least " + minAge + " years old.");

        customer.setFirstname(kycRequestDTO.getFirstname());
        customer.setLastname(kycRequestDTO.getLastname());
        customer.setDateOfBirth(kycRequestDTO.getDateOfBirth());
        customer.setKycCompleted(true);
        customerRepository.save(customer);

        return KycResponseDTO.builder()
                .kycCompleted(true)
                .build();
    }

    public CustomerProfileDTO getUserProfile() {
        Customer customer = authService.getAuthenticatedCustomer();
        return new CustomerProfileDTO(customer.getName(), customer.getLastName(), customer.getEmail());
    }


    public List<TransactionDTO> getUserTransactions() {

        Customer customer = authService.getAuthenticatedCustomer();

        List<Cart> carts = cartRepository.findByUserId(customer.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        if (carts.isEmpty()) {
            throw new RuntimeException("No carts found for user");
        }

        return carts.stream()
                .map(cart -> new TransactionDTO(cart.getId(), new Date()))
                .collect(Collectors.toList());

    }
}
