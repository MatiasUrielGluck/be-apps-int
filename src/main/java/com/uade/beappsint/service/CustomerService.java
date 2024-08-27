package com.uade.beappsint.service;

import com.uade.beappsint.dto.kyc.KycRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.CustomerGraphRepository;
import com.uade.beappsint.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerGraphRepository customerGraphRepository;
    private final AuthService authService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerGraphRepository customerGraphRepository, AuthService authService) {
        this.customerRepository = customerRepository;
        this.customerGraphRepository = customerGraphRepository;
        this.authService = authService;
    }

    public Customer findByEmailInGraph(String email) {
        return customerGraphRepository.findByEmailInGraph(email);
    }

    public List<Customer> findAllClientsInGraph() {
        return customerGraphRepository.findAllClientsInGraph();
    }

    public Customer findClientByUsernameInGraph(String username) {
        return customerGraphRepository.findClientByUsernameInGraph(username);
    }

    public Customer findClientByEmailInGraph(String email) {
        return customerGraphRepository.findClientByEmailInGraph(email);
    }

    public List<Product> findRecentlyViewedProductsInGraph(String username) {
        return customerGraphRepository.findRecentlyViewedProductsInGraph(username);
    }

    public List<Product> findPurchasesByClientInGraph(String username) {
        return customerGraphRepository.findPurchasesByClientInGraph(username);
    }

    public Customer createClientInGraph(String username, String email, String password, String firstName, String lastName) {
        return customerGraphRepository.createClientInGraph(username, email, password, firstName, lastName);
    }

    public Customer updateClientInGraph(String username, String email, String firstName, String lastName) {
        return customerGraphRepository.updateClientInGraph(username, email, firstName, lastName);
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
