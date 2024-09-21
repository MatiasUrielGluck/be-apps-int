package com.uade.beappsint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {
    private Integer requestId;      // ID de la solicitud
    private Integer customerId;     // ID del cliente que solicita el rol de administrador
    private boolean approved;        // Estado de aprobación de la solicitud
    private String requestDate;      // Fecha de la solicitud
    private String approvalDate;     // Fecha de aprobación (si aplica)
}