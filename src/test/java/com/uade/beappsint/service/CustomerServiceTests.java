package com.uade.beappsint.service;

import com.uade.beappsint.dto.kyc.KycRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {
    @Mock
    private AuthService authService;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, authService); // Manually inject the mocks
    }

    @Test
    void testBasicKyc_Success() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setKycCompleted(false);

        KycRequestDTO kycRequestDTO = KycRequestDTO.builder()
                .firstname("John")
                .lastname("Doe")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .build();

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);

        // Act
        KycResponseDTO response = customerService.basicKyc(kycRequestDTO);

        // Assert
        assertTrue(response.isKycCompleted());
        assertEquals("John", mockCustomer.getFirstname());
        assertEquals("Doe", mockCustomer.getLastname());
        assertEquals(LocalDate.of(2000, 1, 1), mockCustomer.getDateOfBirth());
        assertTrue(mockCustomer.isKycCompleted());
    }

    @Test
    void testBasicKyc_InvalidDob_Fail() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setKycCompleted(false);

        KycRequestDTO kycRequestDTO = KycRequestDTO.builder()
                .firstname("John")
                .lastname("Doe")
                .dateOfBirth(LocalDate.now())
                .build();

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);

        // Act and Assert
        assertThrowsExactly(BadRequestException.class, () -> customerService.basicKyc(kycRequestDTO));
        verify(customerRepository, never()).save(Mockito.any(Customer.class));
    }
}
