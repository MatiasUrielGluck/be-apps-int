package com.uade.beappsint.dto.kyc;

import com.uade.beappsint.enums.KycStatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KycResponseDTO {
    private KycStatusEnum kycStatus;
}
