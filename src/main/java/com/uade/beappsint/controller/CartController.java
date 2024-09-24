package com.uade.beappsint.controller;

import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.dto.cart.CartDTO;
import org.springframework.http.ResponseEntity;

/**
 * Controller interface for cart-related operations.
 */
public interface CartController {

    /**
     * Adds a product to the cart.
     *
     * @param addRequestDTO the request data for adding a product to the cart.
     * @return a ResponseEntity containing the updated Cart after adding the product.
     */
    ResponseEntity<CartDTO> addProductToCart(AddRequestDTO addRequestDTO);

    /**
     * Removes a product from the cart by its ID.
     *
     * @param productId the ID of the product to be removed.
     * @return a ResponseEntity containing the updated Cart after removing the product.
     */
    ResponseEntity<CartDTO> removeProductFromCart(Long productId);

    /**
     * Reduces the quantity of a product in the cart by one.
     *
     * @param productId the ID of the product to be reduced.
     * @return a ResponseEntity containing the updated Cart after reducing the product quantity.
     */
    ResponseEntity<CartDTO> removeOneProductFromCart(Long productId);

    /**
     * Clears all products from the cart.
     *
     * @return a ResponseEntity containing the updated Cart after clearing all products.
     */
    ResponseEntity<CartDTO> clearCart();

    /**
     * Checks out the cart, finalizing the purchase.
     *
     * @return a ResponseEntity containing the updated Cart after checkout.
     */
    ResponseEntity<CartDTO> checkoutCart();

    /**
     * Retrieves the current user's cart.
     *
     * @return a ResponseEntity containing the user's cart.
     */
    ResponseEntity<CartDTO> getCart();
}
