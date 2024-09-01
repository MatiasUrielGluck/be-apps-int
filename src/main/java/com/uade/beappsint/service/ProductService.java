package com.uade.beappsint.service;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final AuthService authService;


    public ProductService(ProductRepository productRepository, CustomerRepository customerRepository, AuthService authService) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.authService = authService;
    }

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
        Product savedProduct = productRepository.save(product);
        return savedProduct.toDTO();
    }

    public ProductDTO updateProduct(Long id, Product productDetails) {
        assertAdmin();
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setStock(productDetails.getStock());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setImageUrl(productDetails.getImageUrl());
        product.setYear(productDetails.getYear());
        product.setDirector(productDetails.getDirector());
        product.setCreatedBy(productDetails.getCreatedBy());
        Product updatedProduct = productRepository.save(product);
        return updatedProduct.toDTO();
    }

    public void updateProductStock(Long id, int newStock) {
        Customer customer = assertAdmin();
        isProductCreator(id, customer);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStock(newStock);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Customer customer = assertAdmin();
        isProductCreator(id, customer);
        productRepository.deleteById(id);
    }

    public void viewProduct(Long productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setViews(product.getViews() + 1);
        productRepository.save(product);

        customer.getRecentlyViewedProducts().add(product);
        customerRepository.save(customer);
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
        return customer.getRecentlyViewedProducts()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public void addProductToFavorites(Long productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        if (!customer.getFavoriteProducts().contains(product)) {
            customer.getFavoriteProducts().add(product);
            customerRepository.save(customer);
        }
    }

    public void removeProductFromFavorites(Long productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        if (customer.getFavoriteProducts().contains(product)) {
            customer.getFavoriteProducts().remove(product);
            customerRepository.save(customer);
        }
    }

    public List<ProductDTO> getFavoriteProducts() {
        Customer customer = authService.getAuthenticatedCustomer();
        return customer.getFavoriteProducts()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchProductsByName(String keyword) {
        return productRepository.findByNameContaining(keyword)
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    private Customer assertAdmin() {
        Customer customer = authService.getAuthenticatedCustomer();
        if (!customer.getAdminStatus()) {
            throw new RuntimeException("Access denied: only administrators can perform this action.");
        }
        return customer;
    }

    private void isProductCreator(Long productId, Customer customer) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getCreatedBy().equals(customer)) {
            throw new RuntimeException("Access denied: you are not the creator of this product.");
        }
    }
}