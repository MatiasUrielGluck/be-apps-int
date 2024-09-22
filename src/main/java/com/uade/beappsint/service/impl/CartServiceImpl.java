package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.entity.Cart;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.repository.CartRepository;
import com.uade.beappsint.repository.ProductRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CartService interface.
 * Provides methods for handling cart-related operations.
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final AuthService authService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    /**
     * Adds a product to the authenticated user's cart.
     *
     * @param addRequestDTO the request data transfer object containing the product ID to add
     * @return the updated cart
     */
    @Override
    public Cart addProductToCart(AddRequestDTO addRequestDTO) {
        return null;
    }

    /**
     * Removes a product from the authenticated user's cart.
     *
     * @param productId the ID of the product to remove
     * @return the updated cart
     */
    @Override
    public Cart removeProductFromCart(Long productId) {
        return null;
    }

    /**
     * Clears all products from the authenticated user's cart.
     *
     * @return the updated cart
     */
    @Override
    public Cart clearCart() {
        return null;
    }

    /**
     * Checks out the authenticated user's cart, reducing stock for each product.
     *
     * @return the updated cart
     */
    @Override
    public Cart checkoutCart() {
        return null;
    }

    /**
     * Retrieves the authenticated user's cart.
     *
     * @return the user's cart
     */
    @Override
    public Cart getUserCart() {
        return null;
    }

    /*
    TODO: ** -- ** -- ** -- ** -- ** -- **
    TODO: Implementación anterior. Luego de la migración de arquitectura, utilizar la firma del interface.
    TODO: Para obtener el userId utilizar el método getAuthenticatedCustomer del authService. Ver ejemplo en Product.
    TODO: ** -- ** -- ** -- ** -- ** -- **
    public Cart addProductToCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProducts().add(product);
        cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProducts().remove(product);
        cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());
        return cartRepository.save(cart);
    }

    public Cart clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getProducts().clear();
        cart.setTotalPrice(0);
        return cartRepository.save(cart);
    }

    public Cart checkoutCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        for (Product product : cart.getProducts()) {
            if (product.getStock() < 1) {
                throw new RuntimeException("Product out of stock: " + product.getName());
            }
            product.setStock(product.getStock() - 1);
            productRepository.save(product);
        }
        cart.getProducts().clear();
        cart.setTotalPrice(0);
        return cartRepository.save(cart);
    }
     */
}
