package com.uade.beappsint.service;

import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO createProduct(Product product);

    ProductDTO updateProduct(Long id, Product productDetails);

    void deleteProduct(Long id);

    void viewProduct(Long productId);

    List<ProductDTO> getFeaturedProducts();

    List<ProductDTO> getProductsByCategory(String category);

    List<ProductDTO> getRecentlyViewedProducts();

    Customer assertAdmin();

    void isProductCreator(Long productId, Customer customer);
}
