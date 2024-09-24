package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.ProductController;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Implementation of the ProductController interface.
 * Provides endpoints for managing products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    /**
     * Implementation of the ProductController interface.
     * Provides endpoints for managing products.
     */
    public ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Returns all products in the database.
     *
     * @return a ResponseEntity containing a list of all product data transfer objects
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Returns a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return a ResponseEntity containing the product data transfer object
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Creates a new product. Only admins can perform this action.
     *
     * @param product the product entity to create
     * @return a ResponseEntity containing the created product data transfer object
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    /**
     * Updates an existing product. Only admins can perform this action.
     *
     * @param id the ID of the product to update
     * @param productDetails the updated product entity
     * @return a ResponseEntity containing the updated product data transfer object
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    /**
     * Deletes a product. Only admins can perform this action.
     *
     * @param id the ID of the product to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Associates a product with the logged user by its ID.
     *
     * @param id the ID of the product to view
     * @return a ResponseEntity with no content
     */
    @PostMapping("/{id}/view")
    public ResponseEntity<Void> viewProduct(@PathVariable Long id) {
        productService.viewProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns the featured products.
     *
     * @return a ResponseEntity containing a list of featured product data transfer objects
     */
    @GetMapping("/featured")
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts() {
        List<ProductDTO> featuredProducts = productService.getFeaturedProducts();
        return ResponseEntity.ok(featuredProducts);
    }

    /**
     * Returns products by category.
     *
     * @param category the category of the products to retrieve
     * @return a ResponseEntity containing a list of product data transfer objects in the specified category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<ProductDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    /**
     * Returns the user's recently viewed products.
     *
     * @return a ResponseEntity containing a list of recently viewed product data transfer objects
     */
    @GetMapping("/recently-viewed")
    public ResponseEntity<List<ProductDTO>> getRecentlyViewedProducts() {
        List<ProductDTO> recentlyViewedProducts = productService.getRecentlyViewedProducts();
        return ResponseEntity.ok(recentlyViewedProducts);
    }
}
