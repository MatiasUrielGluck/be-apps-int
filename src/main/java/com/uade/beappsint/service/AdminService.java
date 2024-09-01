package com.uade.beappsint.service;

import com.uade.beappsint.dto.GenericResponseDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final CustomerRepository customerRepository;
    private final AuthService authService;

    public AdminService(CustomerRepository customerRepository, AuthService authService) {
        this.customerRepository = customerRepository;
        this.authService = authService;
    }

    public GenericResponseDTO approveAdmin(Integer customerId) {
        Customer customer = authService.getAuthenticatedCustomer();
        if (!customer.isAdmin()) throw new BadRequestException("Must be admin to approve admin users.");

        Customer toBeAdmin = customerRepository
                .findById(customerId).orElseThrow(() -> new BadRequestException("Customer not found."));

        toBeAdmin.setAdmin(true);
        customerRepository.save(toBeAdmin);
        return GenericResponseDTO.builder()
                .message("Successfully approved admin.")
                .extra("approved")
                .build();
    }
}
