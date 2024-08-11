package com.uade.beappsint.service;

import com.uade.beappsint.dto.kyc.KycRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthService authService;

    public CustomerService(CustomerRepository customerRepository, AuthService authService) {
        this.customerRepository = customerRepository;
        this.authService = authService;
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
}
