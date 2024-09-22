package com.uade.beappsint.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for generic responses.
 * Contains a message and an extra object for additional information.
 */
@Data
@Builder
public class GenericResponseDTO {
    private String message;
    private Object extra;
}
