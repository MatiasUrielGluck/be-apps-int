package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.repository.ProductRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final AuthService authService;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return product.toDTO();
    }

    public ProductDTO createProduct(Product product) {
        assertAdmin();
        product.setCreatedBy(authService.getAuthenticatedCustomer());
        Product savedProduct = productRepository.save(product);
        return savedProduct.toDTO();
    }

    public ProductDTO updateProduct(Long id, Product productDetails) {
        Customer customer = assertAdmin();
        isProductCreator(id, customer);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setStock(productDetails.getStock());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setImageUrl(productDetails.getImageUrl());
        Product updatedProduct = productRepository.save(product);
        return updatedProduct.toDTO();
    }

    public void deleteProduct(Long id) {
        assertAdmin();
        isProductCreator(id, authService.getAuthenticatedCustomer());
        productRepository.deleteById(id);
    }

    public void viewProduct(Long productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setViews(product.getViews() + 1);
        productRepository.save(product);

        if (!customer.getRecentlyViewedProducts().contains(product)) {
            customer.getRecentlyViewedProducts().add(product);
            customerRepository.save(customer);
        }
    }

    public List<ProductDTO> getFeaturedProducts() {
        return productRepository.findTop10ByOrderByViewsDesc()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getRecentlyViewedProducts() {
        Customer customer = authService.getAuthenticatedCustomer();
        List<Product> recentlyViewedProducts = customer.getRecentlyViewedProducts();
        Collections.reverse(recentlyViewedProducts);
        return recentlyViewedProducts
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public Customer assertAdmin() {
        Customer customer = authService.getAuthenticatedCustomer();
        if (!customer.isAdmin()) {
            throw new RuntimeException("Access denied: only administrators can perform this action.");
        }
        return customer;
    }

    public void isProductCreator(Long productId, Customer customer) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException("Product not found"));

        if (!product.getCreatedBy().getId().equals(customer.getId())) {
            throw new RuntimeException("Access denied: you are not the creator of this product.");
        }
    }
}
