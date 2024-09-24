package com.uade.beappsint.dto.auth;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for login responses.
 * Contains the token and expiration time for the authentication session.
 */
@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private Long expiresIn;
}
