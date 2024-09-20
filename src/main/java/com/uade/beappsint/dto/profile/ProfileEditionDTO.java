package com.uade.beappsint.dto.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileEditionDTO {
    private String streetName;
    private String streetNumber;
    private String complementaryAddress;
    private String phoneNumber;
}
