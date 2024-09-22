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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final AuthService authService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartDTO addProductToCart(AddRequestDTO addRequestDTO) {
        Customer customer = authService.getAuthenticatedCustomer();
        Product product = productRepository.findById(addRequestDTO.getProductId()).orElseThrow(() -> new BadRequestException("Product not found"));

        if (product.getStock() - addRequestDTO.getAmount() < 0) throw new BadRequestException("Not enough stock");

        Cart cart = cartRepository.findByCustomerId(customer.getId()).orElse(null);

        if (cart == null) {
            Customer attachedCustomer = customerRepository.findById(customer.getId()).orElseThrow(() -> new RuntimeException("Customer not found"));

            cart = Cart.builder()
                .customer(attachedCustomer)
                .totalPrice(0)
                .cartItems(new ArrayList<>())
                .build();
            cartRepository.save(cart);
        }

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        Optional<CartItem> optionalCartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();
        CartItem cartItem = optionalCartItem.orElse(null);

        if (cartItem == null) {
            cartItem = CartItem.builder()
                .product(product)
                .cart(cart)
                .build();
        }

        cartItem.setQuantity(addRequestDTO.getAmount());
        cartItemRepository.save(cartItem);

        if (optionalCartItem.isEmpty()) {
            cart.getCartItems().add(cartItem);
        }

        cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * addRequestDTO.getAmount());

        Cart savedCart = cartRepository.save(cart);
        productRepository.save(product);
        return savedCart.toDTO();
    }

    @Override
    public CartDTO removeProductFromCart(Long productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new BadRequestException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        CartItem cartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Product not found in cart"));

        cart.getCartItems().remove(cartItem);

        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getPrice() * cartItem.getQuantity());

        Product product = cartItem.getProduct();
        productRepository.save(product);

        cartItemRepository.delete(cartItem);

        Cart savedCart = cartRepository.save(cart);
        return savedCart.toDTO();
    }

    @Override
    public CartDTO clearCart() {
        Customer customer = authService.getAuthenticatedCustomer();
        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new BadRequestException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            productRepository.save(product);
        }

        cart.getCartItems().clear();

        cart.setTotalPrice(0);

        cartItemRepository.deleteAll(cartItems);

        Cart savedCart = cartRepository.save(cart);
        return savedCart.toDTO();
    }

    @Override
    public CartDTO removeOneProductFromCart(Long productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new BadRequestException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        CartItem cartItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Product not found in cart"));

        Product product = cartItem.getProduct();

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemRepository.save(cartItem);
        } else {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }

        cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());

        productRepository.save(product);
        Cart savedCart = cartRepository.save(cart);

        return savedCart.toDTO();
    }

    @Override
    public CartDTO checkoutCart() {
        Customer customer = authService.getAuthenticatedCustomer();
        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new BadRequestException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new BadRequestException("Not enough stock for product: " + product.getName());
            }
        }

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        clearCart();
        Cart savedCart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new BadRequestException("Cart not found"));
        return savedCart.toDTO();
    }

    @Override
    public CartDTO getUserCart() {
        Customer customer = authService.getAuthenticatedCustomer();
        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new BadRequestException("Cart not found"));
        return cart.toDTO();
    }
}
