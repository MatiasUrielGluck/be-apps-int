package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.GenericResponseDTO;
import com.uade.beappsint.dto.ImageDTO;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.entity.Image;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.exception.ResourceNotFoundException;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.repository.ImageRepository;
import com.uade.beappsint.repository.ProductRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ImageRepository imageRepository;
    private final AuthService authService;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return product.toDTO();
    }

    public ProductDTO createProduct(Product product) {
        assertAdmin();
        product.setCreatedBy(authService.getAuthenticatedCustomer());
        Product savedProduct = productRepository.save(product);
        return savedProduct.toDTO();
    }

    public ProductDTO updateProduct(Long id, Product productDetails) {
        Customer customer = assertAdmin();
        isProductCreator(id, customer);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setStock(productDetails.getStock());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setImageUrl(productDetails.getImageUrl());
        Product updatedProduct = productRepository.save(product);
        return updatedProduct.toDTO();
    }

    public void deleteProduct(Long id) {
        assertAdmin();
        isProductCreator(id, authService.getAuthenticatedCustomer());
        productRepository.deleteAllByProductId(id);
        productRepository.deleteById(id);
    }

    public void viewProduct(Long productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        boolean customerAlreadyViewed = !customer.getRecentlyViewedProducts()
                .stream()
                .filter(prod -> prod.getId().equals(product.getId()))
                .toList()
                .isEmpty();

        product.setViews(product.getViews() + 1);
        productRepository.save(product);

        if (customerAlreadyViewed) return;

        customer.getRecentlyViewedProducts().add(product);
        customerRepository.save(customer);
    }

    public List<ProductDTO> getFeaturedProducts() {
        return productRepository.findTop10ByOrderByViewsDesc()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getRecentlyViewedProducts() {
        Customer customer = authService.getAuthenticatedCustomer();
        return customer.getRecentlyViewedProducts()
                .stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public Customer assertAdmin() {
        Customer customer = authService.getAuthenticatedCustomer();
        if (!customer.isAdmin()) {
            throw new RuntimeException("Access denied: only administrators can perform this action.");
        }
        return customer;
    }

    public void isProductCreator(Long productId, Customer customer) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException("Product not found"));

        if (!product.getCreatedBy().getId().equals(customer.getId())) {
            throw new RuntimeException("Access denied: you are not the creator of this product.");
        }
    }

    public List<ProductDTO> searchProductsByName(String partialName) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(partialName);
        return products.stream().map(Product::toDTO).collect(Collectors.toList());
    }

    public List<ProductDTO> getRecommendations(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        List<Product> recommendationsByCategory = productRepository.findRecommendationsByCategory(product.getCategory(), productId);
        List<Product> recommendationsByDecade = productRepository.findRecommendationsByDecade(product.getYear() - 10, product.getYear(), productId);
        List<Product> recommendationsByDirector = productRepository.findRecommendationsByDirector(product.getDirector(), productId);

        Set<Product> recommendations = new HashSet<>(recommendationsByCategory);
        recommendations.addAll(recommendationsByDecade);
        recommendations.addAll(recommendationsByDirector);

        return recommendations.stream()
                .map(Product::toDTO)
                .collect(Collectors.toList());
    }

    public List<ImageDTO> getImagesByProductId(Long productId) {
        List<Image> images = productRepository.findImagesByProductId(productId);
        return images.stream()
                .map(Image::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addImageToProduct(Long productId, ImageDTO imageDTO) {

        Customer customer = assertAdmin();
        isProductCreator(productId, customer);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Image newImage = new Image();
        newImage.setUrl(imageDTO.getUrl());
        newImage.setProduct(product);

        imageRepository.save(newImage);
    }

    public void changeMainImageOfProduct(Long productId, ImageDTO imageDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Customer customer = assertAdmin();
        isProductCreator(productId, customer);
        productRepository.addImageToProduct(imageDTO.getUrl(), productId);
    }

    public GenericResponseDTO toggleFavorite(Long productId) {
        Customer customer = authService.getAuthenticatedCustomer();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        List<Product> favoriteProducts = customer.getFavoriteProducts();

        if (favoriteProducts.stream().anyMatch(prod -> prod.getId().equals(productId))) {
            customer.setFavoriteProducts(favoriteProducts.stream().filter(prod -> !prod.getId().equals(productId)).collect(Collectors.toList()));
        } else {
            favoriteProducts.add(product);
        }

        customerRepository.save(customer);

        return GenericResponseDTO.builder()
                .message("OK")
                .build();
    }

    public List<Long> getFavorites() {
        Customer customer = authService.getAuthenticatedCustomer();
        List<Long> productIdList = new ArrayList<>();
        List<Product> favoriteProducts = customer.getFavoriteProducts();

        for (Product product : favoriteProducts) {
            productIdList.add(product.getId());
        }

        return productIdList;
    }
}
