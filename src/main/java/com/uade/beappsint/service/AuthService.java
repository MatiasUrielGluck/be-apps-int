package com.uade.beappsint.service;

import com.uade.beappsint.dto.auth.*;
import com.uade.beappsint.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    SignupResponseDTO signup(SignupRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);

    Customer getAuthenticatedCustomer();

    CustomerInfoDTO getCustomerInfo();
}
