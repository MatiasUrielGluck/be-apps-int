package com.uade.beappsint.controller;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductController {
    ResponseEntity<List<ProductDTO>> getAllProducts();

    ResponseEntity<ProductDTO> getProductById(Long id);

    ResponseEntity<ProductDTO> createProduct(Product product);

    ResponseEntity<ProductDTO> updateProduct(Long id, Product productDetails);

    ResponseEntity<Void> deleteProduct(Long id);

    ResponseEntity<Void> viewProduct(Long id);

    ResponseEntity<List<ProductDTO>> getFeaturedProducts();

    ResponseEntity<List<ProductDTO>> getProductsByCategory(String category);

    ResponseEntity<List<ProductDTO>> getRecentlyViewedProducts();
}
