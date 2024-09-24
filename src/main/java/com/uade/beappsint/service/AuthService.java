package com.uade.beappsint.service;

import com.uade.beappsint.dto.auth.*;
import com.uade.beappsint.entity.Customer;

/**
 * Service interface for authentication-related operations.
 */
public interface AuthService {

    /**
     * Registers a new customer with the provided signup request data.
     *
     * @param request the signup request data.
     * @return the response data after successful signup.
     */
    SignupResponseDTO signup(SignupRequestDTO request);

    /**
     * Authenticates a customer with the provided login request data.
     *
     * @param request the login request data.
     * @return the response data after successful login.
     */
    LoginResponseDTO login(LoginRequestDTO request);

    /**
     * Retrieves the currently authenticated customer.
     *
     * @return the authenticated customer.
     */
    Customer getAuthenticatedCustomer();

    /**
     * Retrieves the information of the currently authenticated customer.
     *
     * @return the customer information.
     */
    CustomerInfoDTO getCustomerInfo();
}
