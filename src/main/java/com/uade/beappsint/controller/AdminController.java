package com.uade.beappsint.controller;

import com.uade.beappsint.dto.GenericResponseDTO;
import com.uade.beappsint.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/approval/{customerId}")
    public ResponseEntity<GenericResponseDTO> approve(@PathVariable("customerId") Integer customerId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(adminService.approveAdmin(customerId));
    }
}
