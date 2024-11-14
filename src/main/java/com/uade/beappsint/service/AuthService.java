package com.uade.beappsint.service;

import com.uade.beappsint.dto.GenericResponseDTO;
import com.uade.beappsint.dto.auth.*;
import com.uade.beappsint.entity.Customer;

public interface AuthService {
    SignupResponseDTO signup(SignupRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);

    Customer getAuthenticatedCustomer();

    CustomerInfoDTO getCustomerInfo();

    GenericResponseDTO resendVerificationCode();

    GenericResponseDTO verifyVerificationCode(VerificationCodeDTO request);
}
