package com.uade.beappsint.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private Long expiresIn;
}
