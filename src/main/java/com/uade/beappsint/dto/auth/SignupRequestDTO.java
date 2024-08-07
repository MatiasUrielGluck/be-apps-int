package com.uade.beappsint.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupRequestDTO {
    @Email @NotNull @NotEmpty
    private String email;

    private String password;
}
