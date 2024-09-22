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

        product.setStock(product.getStock() - addRequestDTO.getAmount());
        Cart savedCart = cartRepository.save(cart);
        productRepository.save(product);
        return savedCart.toDTO();
    }

    @Override
    public CartDTO removeProductFromCart(Long productId) {
        /*
        Customer customer = authService.getAuthenticatedCustomer();
        Product product = productRepository.findById(productId).orElseThrow(() -> new BadRequestException("Product not found"));
        Cart cart = cartRepository.findByCustomerId(customer.getId()).orElseThrow(() -> new BadRequestException("Cart not found"));
        cart.setProducts(cart.getProducts().stream().filter(prod -> !Objects.equals(prod.getId(), productId)).collect(Collectors.toList()));
        cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());
        product.setStock(product.getStock() + product.getStock());

        Cart savedCart = cartRepository.save(cart);
        productRepository.save(product);
        return savedCart.toDTO();

         */
        return null;
    }

    @Override
    public CartDTO clearCart() {
        return null;
    }

    @Override
    public CartDTO checkoutCart() {
        return null;
    }

    @Override
    public CartDTO getUserCart() {
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
