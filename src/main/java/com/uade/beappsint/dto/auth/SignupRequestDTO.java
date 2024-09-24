package com.uade.beappsint.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for signup requests.
 * Contains the email and password required for user registration.
 */
@Data
@Builder
public class SignupRequestDTO {
    @Email @NotNull @NotEmpty
    private String email;

    private String password;
}
