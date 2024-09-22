package com.uade.beappsint.controller;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface for product-related endpoints.
 * Provides methods for displaying and managing products.
 */
@Tag(name = "Product", description = "Endpoints for product display and management")
public interface ProductController {

    /**
     * Returns all products in the database.
     *
     * @return a ResponseEntity containing a list of all product data transfer objects
     */
    @Operation(summary = "Return all products", description = "Returns all the database products and their info.")
    ResponseEntity<List<ProductDTO>> getAllProducts();

    /**
     * Returns a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return a ResponseEntity containing the product data transfer object
     */
    @Operation(summary = "Return a product by the ID", description = "Returns a product by the ID.")
    ResponseEntity<ProductDTO> getProductById(Long id);

    /**
     * Creates a new product. Only admins can perform this action.
     *
     * @param product the product entity to create
     * @return a ResponseEntity containing the created product data transfer object
     */
    @Operation(summary = "Create a product", description = "Create a product. Only admins can perform this action.")
    ResponseEntity<ProductDTO> createProduct(Product product);

    /**
     * Updates an existing product. Only admins can perform this action.
     *
     * @param id the ID of the product to update
     * @param productDetails the updated product entity
     * @return a ResponseEntity containing the updated product data transfer object
     */
    @Operation(summary = "update a product", description = "update a product. Only admins can perform this action.")
    ResponseEntity<ProductDTO> updateProduct(Long id, Product productDetails);

    /**
     * Deletes a product. Only admins can perform this action.
     *
     * @param id the ID of the product to delete
     * @return a ResponseEntity with no content
     */
    @Operation(summary = "Delete a product", description = "Delete a product. Only admins can perform this action.")
    ResponseEntity<Void> deleteProduct(Long id);

    /**
     * Associates a product with the logged user by its ID.
     *
     * @param id the ID of the product to view
     * @return a ResponseEntity with no content
     */
    @Operation(summary = "View a product", description = "Receives the product id and associates the product with the logged user.")
    ResponseEntity<Void> viewProduct(Long id);

    /**
     * Returns the featured products.
     *
     * @return a ResponseEntity containing a list of featured product data transfer objects
     */
    @Operation(summary = "Return the featured products", description = "Return the actual featured products.")
    ResponseEntity<List<ProductDTO>> getFeaturedProducts();

    /**
     * Returns products by category.
     *
     * @param category the category of the products to retrieve
     * @return a ResponseEntity containing a list of product data transfer objects in the specified category
     */
    @Operation(summary = "Return the category products", description = "Return the category products.")
    ResponseEntity<List<ProductDTO>> getProductsByCategory(String category);

    /**
     * Returns the user's recently viewed products.
     *
     * @return a ResponseEntity containing a list of recently viewed product data transfer objects
     */
    @Operation(summary = "Return the user's recently viewed products", description = "Return the user's recently viewed products.")
    ResponseEntity<List<ProductDTO>> getRecentlyViewedProducts();
}
