package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.ProductController;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    public ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<Void> viewProduct(@PathVariable Long id) {
        productService.viewProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/featured")
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts() {
        List<ProductDTO> featuredProducts = productService.getFeaturedProducts();
        return ResponseEntity.ok(featuredProducts);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<ProductDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/recently-viewed")
    public ResponseEntity<List<ProductDTO>> getRecentlyViewedProducts() {
        List<ProductDTO> recentlyViewedProducts = productService.getRecentlyViewedProducts();
        return ResponseEntity.ok(recentlyViewedProducts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String partialName) {
        List<ProductDTO> products = productService.searchProductsByName(partialName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<ProductDTO>> getRecommendations(@PathVariable Long id) {
        List<ProductDTO> recommendations = productService.getRecommendations(id);
        return ResponseEntity.ok(recommendations);
    }

}
