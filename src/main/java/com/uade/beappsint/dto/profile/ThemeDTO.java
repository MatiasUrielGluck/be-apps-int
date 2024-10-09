package com.uade.beappsint.dto.profile;

import com.uade.beappsint.enums.ThemeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ThemeDTO {
    private ThemeEnum theme;
}
