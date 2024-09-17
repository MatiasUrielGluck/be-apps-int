package com.uade.beappsint.service;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.repository.CustomerGraphRepository;
import com.uade.beappsint.repository.ProductGraphRepository;
import com.uade.beappsint.entity.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductGraphService {
    private final ProductGraphRepository productRepository;
    private final CustomerGraphRepository customerRepository;
    private final AuthService authService;

    public ProductGraphService(ProductGraphRepository productRepository, CustomerGraphRepository customerRepository, AuthService authService) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.authService = authService;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAllProductsInGraph()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(String code) { // Assuming code is the identifier in Neo4j
        Product product = productRepository.findProductByCodeInGraph(code);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return product.toDTO();
    }

    public ProductDTO createProduct(Product product) {
        assertAdmin();
        Product savedProduct = productRepository.createProductInGraph(
                product.getCode(), product.getName(), product.getDescription(), product.getStock(), product.getPrice(), product.getCategory());
        return savedProduct.toDTO();
    }

    public ProductDTO updateProduct(String code, Product productDetails) {
        assertAdmin();
        Product product = productRepository.findProductByCodeInGraph(code);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setStock(productDetails.getStock());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        Product updatedProduct = productRepository.updateProductInGraph(code, product.getName(), product.getDescription(),
                product.getStock(), product.getPrice(), product.getCategory());
        return updatedProduct.toDTO();
    }

    public void updateProductStock(String code, int newStock) {
        assertAdmin();
        Product product = productRepository.findProductByCodeInGraph(code);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        product.setStock(newStock);
        productRepository.save(product); // Leverage Spring Data Neo4j save method
    }

    public void deleteProduct(String code) {
        assertAdmin();
        productRepository.deleteProductInGraph(code);
    }

    public void viewProduct(Long productId) { // Assuming productId is not used in Neo4j
        Customer customer = authService.getAuthenticatedCustomer();
        String username = customer.getUsername(); // Assuming username is used for relationships

        // Update product views (consider Cypher query if needed for complex logic)
        Product product = productRepository.findProductByCodeInGraph(String.valueOf(productId)); // Assuming conversion needed
        if (product != null) {
            product.setViews(product.getViews() + 1);
            productRepository.save(product);
        }

        // Add product to recently viewed for the customer (using CustomerGraphRepository)
        customerRepository.addProductToRecentlyViewedInGraph(username, String.valueOf(productId)); // Assuming conversion needed
    }

    public List<ProductDTO> getFeaturedProducts() {
        return productRepository.findHighlightedProductsInGraph()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }


    public List<ProductDTO> getRecentlyViewedProducts() {
        Customer customer = authService.getAuthenticatedCustomer();
        String username = customer.getUsername();
        return customerRepository.findRecentlyViewedProductsByUserInGraph(username)
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public void addProductToFavorites(String productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        String username = customer.getUsername();
        customerRepository.addProductToFavoritesInGraph(username, productId);
    }

    public void removeProductFromFavorites(String productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        String username = customer.getUsername();
        customerRepository.deleteFavoriteProduct(username, productId);
    }

    private void assertAdmin() {
        Customer customer = authService.getAuthenticatedCustomer();
        if (!customer.getAdminStatus()) {
            throw new RuntimeException("Access denied: only administrators can perform this action.");
        }
    }
}