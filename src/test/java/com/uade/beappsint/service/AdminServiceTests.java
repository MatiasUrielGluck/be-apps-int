package com.uade.beappsint.service;

import com.uade.beappsint.dto.GenericResponseDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.lenient;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdminServiceTests {
    @Mock
    private AuthService authService;

    @Mock
    private CustomerRepository customerRepository;

    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminService = new AdminService(customerRepository, authService); // Manually inject the mocks
    }

    @Test
    void testAdminApproval_Success() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setAdmin(true);

        Customer toBeAdmin = new Customer();
        toBeAdmin.setId(1);
        toBeAdmin.setAdmin(false);

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(toBeAdmin));

        // Act
        GenericResponseDTO response = adminService.approveAdmin(1);

        // Assert
        Assertions.assertEquals("approved", response.getExtra());
    }

    @Test
    void testAdminApproval_Fail() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setAdmin(false);

        Customer toBeAdmin = new Customer();
        toBeAdmin.setId(1);
        toBeAdmin.setAdmin(false);

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);
        lenient().when(customerRepository.findById(1)).thenReturn(Optional.of(toBeAdmin));

        // Assert
        Assertions.assertThrows(BadRequestException.class, () -> adminService.approveAdmin(1));
    }
}
