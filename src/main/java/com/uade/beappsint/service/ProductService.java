package com.uade.beappsint.service;

import com.uade.beappsint.entity.Product;
import com.uade.beappsint.repository.ProductGraphRepository;
import com.uade.beappsint.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductGraphRepository productGraphRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductGraphRepository productGraphRepository) {
        this.productRepository = productRepository;
        this.productGraphRepository = productGraphRepository;
    }

    public List<Product> findAllProducts() {
        return productGraphRepository.findAllProductsInGraph();
    }

    public Product findProductByCode(String code) {
        return productGraphRepository.findProductByCodeInGraph(code);
    }

    public Product findProductByName(String name) {
        return productGraphRepository.findProductByNameInGraph(name);
    }

    public List<Product> findFavoriteProductsByClient(String username) {
        return productGraphRepository.findFavoriteProductsByClientInGraph(username);
    }

    public void addProductToRecentlyViewed(String username, String code) {
        productGraphRepository.addProductToRecentlyViewedInGraph(username, code);
    }

    public Product createProduct(String code, String name, String description, int stock, double price, String category) {
        return productGraphRepository.createProductInGraph(code, name, description, stock, price, category);
    }

    public Product updateProduct(String code, String name, String description, int stock, double price, String category) {
        return productGraphRepository.updateProductInGraph(code, name, description, stock, price, category);
    }

    public void deleteProduct(String code) {
        productGraphRepository.deleteProductInGraph(code);
    }

    public List<Product> findHighlightedProducts() {
        return productGraphRepository.findHighlightedProductsInGraph();
    }

    public List<Product> findProductsGroupedByCategory() {
        return productGraphRepository.findProductsGroupedByCategoryInGraph();
    }

    public List<Product> findRecentlyViewedProductsByUser(String username) {
        return productGraphRepository.findRecentlyViewedProductsByUserInGraph(username);
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setStock(productDetails.getStock());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setImageUrl(productDetails.getImageUrl());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}