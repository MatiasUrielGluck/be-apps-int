package com.uade.beappsint.service;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;

import java.util.List;

/**
 * Service interface for product-related operations.
 */
public interface ProductService {

    /**
     * Retrieves all products.
     *
     * @return a list of all products.
     */
    List<ProductDTO> getAllProducts();

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve.
     * @return the product with the specified ID.
     */
    ProductDTO getProductById(Long id);

    /**
     * Creates a new product.
     *
     * @param product the product to create.
     * @return the created product.
     */
    ProductDTO createProduct(Product product);

    /**
     * Updates an existing product.
     *
     * @param id the ID of the product to update.
     * @param productDetails the new details of the product.
     * @return the updated product.
     */
    ProductDTO updateProduct(Long id, Product productDetails);

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete.
     */
    void deleteProduct(Long id);

    /**
     * Records a view of a product.
     *
     * @param productId the ID of the product to view.
     */
    void viewProduct(Long productId);

    /**
     * Retrieves featured products.
     *
     * @return a list of featured products.
     */
    List<ProductDTO> getFeaturedProducts();

    /**
     * Retrieves featured products.
     *
     * @return a list of featured products.
     */
    List<ProductDTO> getProductsByCategory(String category);

    /**
     * Retrieves recently viewed products.
     *
     * @return a list of recently viewed products.
     */
    List<ProductDTO> getRecentlyViewedProducts();

    /**
     * Asserts that the current user is an admin.
     *
     * @return the authenticated admin customer.
     */
    Customer assertAdmin();

    /**
     * Checks if the specified customer is the creator of the product.
     *
     * @param productId the ID of the product.
     * @param customer the customer to check.
     */
    void isProductCreator(Long productId, Customer customer);
}
