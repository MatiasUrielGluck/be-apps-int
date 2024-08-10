package com.uade.beappsint.dto.kyc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KycResponseDTO {
    private boolean kycCompleted;
}
