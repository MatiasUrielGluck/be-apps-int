package com.uade.beappsint.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for login requests.
 * Contains the email and password required for authentication.
 */
@Data
@Builder
public class LoginRequestDTO {
    @NotNull @Email
    private String email;

    @NotNull
    private String password;
}
