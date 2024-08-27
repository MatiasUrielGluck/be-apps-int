package com.uade.beappsint.controller;

import com.uade.beappsint.dto.kyc.KycRequestDTO;
import com.uade.beappsint.dto.kyc.KycResponseDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/customer")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/kyc")
    public ResponseEntity<KycResponseDTO> basicKyc(@RequestBody @Validated KycRequestDTO requestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.basicKyc(requestDTO));
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Customer>> getAllClients() {
        List<Customer> clients = customerService.findAllClientsInGraph();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Customer> getClientByUsername(@PathVariable String username) {
        Customer client = customerService.findClientByUsernameInGraph(username);
        if (client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getClientByEmail(@PathVariable String email) {
        Customer client = customerService.findClientByEmailInGraph(email);
        if (client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{username}/recently-viewed-products")
    public ResponseEntity<List<Product>> getRecentlyViewedProducts(@PathVariable String username) {
        List<Product> products = customerService.findRecentlyViewedProductsInGraph(username);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{username}/purchases")
    public ResponseEntity<List<Product>> getPurchasesByClient(@PathVariable String username) {
        List<Product> purchases = customerService.findPurchasesByClientInGraph(username);
        return ResponseEntity.ok(purchases);
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> createClient(@RequestBody @Validated KycRequestDTO requestDTO) {
        Customer client = customerService.createClientInGraph(requestDTO.getUsername(), requestDTO.getEmail(), requestDTO.getPassword(), requestDTO.getFirstname(), requestDTO.getLastname());
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @PutMapping("/{username}/update")
    public ResponseEntity<Customer> updateClient(@PathVariable String username, @RequestBody @Validated KycRequestDTO requestDTO) {
        Customer updatedClient = customerService.updateClientInGraph(username, requestDTO.getEmail(), requestDTO.getFirstname(), requestDTO.getLastname());
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}