package com.uade.beappsint.dto.auth;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for signup responses.
 * Contains a message indicating the result of the signup process.
 */
@Data
@Builder
public class SignupResponseDTO {
    private String message;
}
