package com.uade.beappsint.dto.kyc;

import com.uade.beappsint.enums.KycStatusEnum;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for KYC (Know Your Customer) responses.
 * Contains the KYC status indicating the result of the KYC process.
 */
@Data
@Builder
public class KycResponseDTO {
    private KycStatusEnum kycStatus;
}
