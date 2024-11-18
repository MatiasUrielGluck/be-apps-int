package com.uade.beappsint.service;

import com.uade.beappsint.dto.AdminRequestDTO;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.dto.ReviewDTO;
import com.uade.beappsint.dto.auth.CustomerInfoDTO;
import com.uade.beappsint.dto.kyc.KycBasicRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.dto.profile.ProfileEditionDTO;
import com.uade.beappsint.entity.AdminRequest;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.entity.Review;
import com.uade.beappsint.enums.KycStatusEnum;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.AdminRequestRepository;
import com.uade.beappsint.repository.CartRepository;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.repository.ReviewRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

    @Mock
    private AuthService authService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private AdminRequestRepository adminRequestRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ProductService productService;

    @Mock
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, productService, authService, adminRequestRepository, reviewRepository, cartRepository);
        // Manually inject the mocks
    }

    @Test
    void testBasicKyc_Success() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setKycStatus(KycStatusEnum.BASIC_KYC);

        KycBasicRequestDTO kycBasicRequestDTO = KycBasicRequestDTO.builder()
                .firstname("John")
                .lastname("Doe")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .build();

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);

        // Act
        KycResponseDTO response = customerService.basicKyc(kycBasicRequestDTO);

        // Assert
        assertEquals(response.getKycStatus(), KycStatusEnum.RESIDENTIAL_KYC);
        assertEquals("John", mockCustomer.getFirstname());
        assertEquals("Doe", mockCustomer.getLastname());
        assertEquals(LocalDate.of(2000, 1, 1), mockCustomer.getDateOfBirth());
        assertEquals(mockCustomer.getKycStatus(), KycStatusEnum.RESIDENTIAL_KYC);
    }

    @Test
    void testBasicKyc_InvalidDob_Fail() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setKycStatus(KycStatusEnum.BASIC_KYC);

        KycBasicRequestDTO kycBasicRequestDTO = KycBasicRequestDTO.builder()
                .firstname("John")
                .lastname("Doe")
                .dateOfBirth(LocalDate.now())
                .build();

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);

        // Act and Assert
        assertThrowsExactly(BadRequestException.class, () -> customerService.basicKyc(kycBasicRequestDTO));
        verify(customerRepository, never()).save(Mockito.any(Customer.class));
    }

    @Test
    void testEditCustomerInfo_Success() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setStreetName("Old Street");
        mockCustomer.setStreetNumber("100");

        ProfileEditionDTO requestDTO = ProfileEditionDTO.builder()
                .streetName("New Street")
                .streetNumber("200")
                .build();

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);
        Mockito.when(customerRepository.save(mockCustomer)).thenReturn(mockCustomer);

        // Act
        CustomerInfoDTO result = customerService.editCustomerInfo(requestDTO);

        // Assert
        assertEquals("New Street", mockCustomer.getStreetName());
        assertEquals("200", mockCustomer.getStreetNumber());  // Change to String
        verify(customerRepository, times(1)).save(mockCustomer);
    }

    @Test
    void testMarkProductAsFavorite_Success() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setKycStatus(KycStatusEnum.BASIC_KYC);

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);
        Mockito.when(customerRepository.addFavoriteProduct(Mockito.anyInt(), Mockito.anyLong())).thenReturn(1);

        // Act
        boolean result = customerService.markProductAsFavorite(mockCustomer.getId(), 101L);

        // Assert
        assertTrue(result);
        verify(customerRepository, times(1)).addFavoriteProduct(mockCustomer.getId(), 101L);
    }

    @Test
    void testMarkProductAsFavorite_FailDueToKycStage() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setKycStatus(KycStatusEnum.COMPLETED_KYC);

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);

        // Act & Assert
        assertThrowsExactly(BadRequestException.class, () -> customerService.markProductAsFavorite(mockCustomer.getId(), 101L));
        verify(customerRepository, never()).addFavoriteProduct(Mockito.anyInt(), Mockito.anyLong());
    }

    @Test
    void testRequestAdminRole_Success() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setKycStatus(KycStatusEnum.BASIC_KYC);

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockCustomer));

        AdminRequest mockAdminRequest = new AdminRequest();
        mockAdminRequest.setCustomer(mockCustomer);
        mockAdminRequest.setStatus(false);
        mockAdminRequest.setRequestDate(LocalDate.now());

        Mockito.when(adminRequestRepository.save(Mockito.any(AdminRequest.class))).thenReturn(mockAdminRequest);

        // Act
        AdminRequestDTO result = customerService.requestAdminRole(1);

        // Assert
        assertNotNull(result);
        assertFalse(result.isApproved());
        verify(adminRequestRepository, times(1)).save(Mockito.any(AdminRequest.class));
    }

    @Test
    void testAddReview_Success() {
        // Arrange
        Customer mockCustomer = new Customer();
        mockCustomer.setKycStatus(KycStatusEnum.BASIC_KYC);

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setProductId(101L);
        reviewDTO.setComment("Great product!");
        reviewDTO.setRating(5);

        ProductDTO mockProductDTO = new ProductDTO();
        mockProductDTO.setId(101L);
        mockProductDTO.setName("Product A");

        Product mockProduct = new Product();
        mockProduct.setId(101L); // Asignar ID
        mockProduct.setName("Product A");

        Review mockReview = new Review();
        mockReview.setComment("Great product!");
        mockReview.setRating(5);
        mockReview.setCustomer(mockCustomer);
        mockReview.setProduct(mockProduct);

        Mockito.when(authService.getAuthenticatedCustomer()).thenReturn(mockCustomer);
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockCustomer));
        Mockito.when(productService.getProductById(Mockito.anyLong())).thenReturn(mockProductDTO);
        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(mockReview); // Mockear el guardado

        // Act
        ReviewDTO result = customerService.addReview(1, reviewDTO);

        // Assert
        assertEquals("Great product!", result.getComment());
        assertEquals(5, result.getRating());
        verify(reviewRepository, times(1)).save(Mockito.any(Review.class));
    }
}
