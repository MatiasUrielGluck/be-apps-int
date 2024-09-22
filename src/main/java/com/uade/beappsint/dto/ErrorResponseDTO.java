package com.uade.beappsint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for error responses.
 * Contains the error message to be returned in case of an error.
 */
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ErrorResponseDTO {
    private String error;
}
