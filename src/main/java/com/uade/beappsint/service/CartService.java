package com.uade.beappsint.service;

import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.entity.Cart;

/**
 * Service interface for cart-related operations.
 */
public interface CartService {

    /**
     * Adds a product to the cart.
     *
     * @param addRequestDTO the request data for adding a product to the cart.
     * @return the updated Cart after adding the product.
     */
    Cart addProductToCart(AddRequestDTO addRequestDTO);

    /**
     * Removes a product from the cart by its ID.
     *
     * @param productId the ID of the product to be removed.
     * @return the updated Cart after removing the product.
     */
    Cart removeProductFromCart(Long productId);

    /**
     * Clears all products from the cart.
     *
     * @return the updated Cart after clearing all products.
     */
    Cart clearCart();

    /**
     * Checks out the cart, finalizing the purchase.
     *
     * @return the updated Cart after checkout.
     */
    Cart checkoutCart();

    /**
     * Retrieves the cart of the currently authenticated user.
     *
     * @return the cart of the authenticated user.
     */
    Cart getUserCart();
}
