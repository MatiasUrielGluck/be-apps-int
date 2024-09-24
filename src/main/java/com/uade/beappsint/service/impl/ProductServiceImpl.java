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

/**
 * Implementation of the ProductService interface.
 * Provides methods for handling product-related operations.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final AuthService authService;

    /**
     * Retrieves all products.
     *
     * @return a list of ProductDTO objects representing all products
     */
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return a ProductDTO object representing the product
     * @throws RuntimeException if the product is not found
     */
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return product.toDTO();
    }

    /**
     * Creates a new product.
     *
     * @param product the product entity to create
     * @return a ProductDTO object representing the created product
     */
    public ProductDTO createProduct(Product product) {
        assertAdmin();
        product.setCreatedBy(authService.getAuthenticatedCustomer());
        Product savedProduct = productRepository.save(product);
        return savedProduct.toDTO();
    }

    /**
     * Updates an existing product.
     *
     * @param id the ID of the product to update
     * @param productDetails the updated product details
     * @return a ProductDTO object representing the updated product
     * @throws RuntimeException if the product is not found
     */
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

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     */
    public void deleteProduct(Long id) {
        assertAdmin();
        isProductCreator(id, authService.getAuthenticatedCustomer());
        productRepository.deleteById(id);
    }

    /**
     * Increments the view count of a product and adds it to the customer's recently viewed products.
     *
     * @param productId the ID of the product to view
     * @throws RuntimeException if the product is not found
     */
    public void viewProduct(Long productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setViews(product.getViews() + 1);
        productRepository.save(product);

        customer.getRecentlyViewedProducts().add(product);
        customerRepository.save(customer);
    }

    /**
     * Retrieves the top 10 most viewed products.
     *
     * @return a list of ProductDTO objects representing the top 10 most viewed products
     */
    public List<ProductDTO> getFeaturedProducts() {
        return productRepository.findTop10ByOrderByViewsDesc()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves products by category.
     *
     * @param category the category to filter products by
     * @return a list of ProductDTO objects representing the products in the specified category
     */
    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the recently viewed products of the authenticated customer.
     *
     * @return a list of ProductDTO objects representing the recently viewed products
     */
    public List<ProductDTO> getRecentlyViewedProducts() {
        Customer customer = authService.getAuthenticatedCustomer();
        return customer.getRecentlyViewedProducts()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Asserts that the authenticated customer is an admin.
     *
     * @return the authenticated customer
     * @throws RuntimeException if the customer is not an admin
     */
    public Customer assertAdmin() {
        Customer customer = authService.getAuthenticatedCustomer();
        if (!customer.isAdmin()) {
            throw new RuntimeException("Access denied: only administrators can perform this action.");
        }
        return customer;
    }

    /**
     * Checks if the authenticated customer is the creator of the product.
     *
     * @param productId the ID of the product to check
     * @param customer the authenticated customer
     * @throws BadRequestException if the product is not found
     * @throws RuntimeException if the customer is not the creator of the product
     */
    public void isProductCreator(Long productId, Customer customer) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException("Product not found"));

        if (!product.getCreatedBy().getId().equals(customer.getId())) {
            throw new RuntimeException("Access denied: you are not the creator of this product.");
        }
    }
}
