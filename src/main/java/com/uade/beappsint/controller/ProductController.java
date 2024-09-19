package com.uade.beappsint.controller;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public interface ProductController {
    @GetMapping
    ResponseEntity<List<ProductDTO>> getAllProducts();

    @GetMapping("/{id}")
    ResponseEntity<ProductDTO> getProductById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<ProductDTO> createProduct(@RequestBody Product product);

    @PutMapping("/{id}")
    ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product productDetails);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable Long id);

    @PostMapping("/{id}/view")
    ResponseEntity<Void> viewProduct(@PathVariable Long id);

    @GetMapping("/featured")
    ResponseEntity<List<ProductDTO>> getFeaturedProducts();

    @GetMapping("/category/{category}")
    ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category);

    @GetMapping("/recently-viewed")
    ResponseEntity<List<ProductDTO>> getRecentlyViewedProducts();
}
