package com.uade.beappsint.entity;

import com.uade.beappsint.dto.AdminRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "admin_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "status", nullable = false)
    private boolean status = false;

    @Column(name = "approved", nullable = false)
    private boolean approved;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    public AdminRequestDTO toDTO() {
        AdminRequestDTO dto = new AdminRequestDTO();
        dto.setId(this.id);
        dto.setCustomerId(this.customer.getId());
        dto.setApproved(this.approved);
        dto.setRequestDate(this.requestDate.toString());
        if (this.approvalDate != null) {
            dto.setApprovalDate(this.approvalDate.toString());
        }
        return dto;
    }
}