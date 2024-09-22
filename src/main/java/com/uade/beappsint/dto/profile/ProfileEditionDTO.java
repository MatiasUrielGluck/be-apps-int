package com.uade.beappsint.dto.profile;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for profile edition.
 * Contains the street name, street number, complementary address, and phone number for the profile.
 */
@Data
@Builder
public class ProfileEditionDTO {
    private String streetName;
    private String streetNumber;
    private String complementaryAddress;
    private String phoneNumber;
}
