package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.cart.AddRequestDTO;
import com.uade.beappsint.dto.cart.CartDTO;
import com.uade.beappsint.entity.Cart;
import com.uade.beappsint.entity.CartItem;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.CartItemRepository;
import com.uade.beappsint.repository.CartRepository;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.repository.ProductRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    private final AuthService authService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartDTO addProductToCart(AddRequestDTO addRequestDTO) {
        Customer customer = getAuthenticatedCustomer();
        Product product = getProductById(addRequestDTO.getProductId());

        validateStockAvailability(product, addRequestDTO.getAmount());

        Cart cart = getOrCreateCartForCustomer(customer);
        CartItem cartItem = getOrCreateCartItem(cart, product);

        updateCartItemQuantity(cartItem, addRequestDTO.getAmount());
        updateCartTotalPrice(cart, product.getPrice() * addRequestDTO.getAmount());

        cartRepository.save(cart);
        return cart.toDTO();
    }

    @Override
    public CartDTO removeProductFromCart(Long productId) {
        Customer customer = getAuthenticatedCustomer();
        Cart cart = getCartByCustomer(customer);

        CartItem cartItem = findCartItemByProductId(cart, productId);
        updateCartTotalPrice(cart, -cartItem.getProduct().getPrice() * cartItem.getQuantity());

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        cartRepository.save(cart);
        return cart.toDTO();
    }

    @Override
    public CartDTO clearCart() {
        Customer customer = getAuthenticatedCustomer();
        Cart cart = getCartByCustomer(customer);

        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setTotalPrice(0);

        cartRepository.save(cart);
        return cart.toDTO();
    }

    @Override
    public CartDTO removeOneProductFromCart(Long productId) {
        Customer customer = getAuthenticatedCustomer();
        Cart cart = getCartByCustomer(customer);

        CartItem cartItem = findCartItemByProductId(cart, productId);

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
        } else {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }

        updateCartTotalPrice(cart, -cartItem.getProduct().getPrice());

        cartRepository.save(cart);
        return cart.toDTO();
    }

    @Override
    public CartDTO checkoutCart() {
        Customer customer = getAuthenticatedCustomer();
        Cart cart = getCartByCustomer(customer);

        cart.getCartItems().forEach(cartItem -> validateStockAvailability(cartItem.getProduct(), cartItem.getQuantity()));

        cart.getCartItems().forEach(cartItem -> {
            Product product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        });

        clearCart();
        return cart.toDTO();
    }

    @Override
    public CartDTO getUserCart() {
        Customer customer = getAuthenticatedCustomer();
        Cart cart = getCartByCustomer(customer);
        return cart.toDTO();
    }

    private Customer getAuthenticatedCustomer() {
        return authService.getAuthenticatedCustomer();
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException("Product not found"));
    }

    private void validateStockAvailability(Product product, int requestedAmount) {
        if (product.getStock() < requestedAmount) {
            throw new BadRequestException("Not enough stock for product: " + product.getName());
        }
    }

    private Cart getOrCreateCartForCustomer(Customer customer) {
        return cartRepository.findByCustomerId(customer.getId())
                .orElseGet(() -> {
                    Customer attachedCustomer = customerRepository.findById(customer.getId()).orElseThrow(() -> new RuntimeException("Cannot find authenticated user. Internal error."));
                    Cart newCart = Cart.builder()
                            .customer(attachedCustomer)
                            .totalPrice(0)
                            .cartItems(new ArrayList<>())
                            .build();
                    return cartRepository.save(newCart);
                });
    }

    private CartItem getOrCreateCartItem(Cart cart, Product product) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newCartItem = CartItem.builder()
                            .product(product)
                            .cart(cart)
                            .quantity(0)
                            .build();
                    cartItemRepository.save(newCartItem);
                    cart.getCartItems().add(newCartItem);
                    return newCartItem;
                });
    }

    private void updateCartItemQuantity(CartItem cartItem, int quantity) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);
    }

    private Cart getCartByCustomer(Customer customer) {
        return cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new BadRequestException("Cart not found"));
    }

    private CartItem findCartItemByProductId(Cart cart, Long productId) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Product not found in cart"));
    }

    private void updateCartTotalPrice(Cart cart, double amount) {
        cart.setTotalPrice(cart.getTotalPrice() + amount);
    }
}
