package com.uade.beappsint.service;

import com.uade.beappsint.dto.GenericResponseDTO;
import com.uade.beappsint.dto.ImageDTO;
import com.uade.beappsint.dto.Product.ProductRequestDTO;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.entity.Customer;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO createProduct(ProductRequestDTO product);

    ProductDTO createProduct_v2(ProductRequestDTO product);

    ProductDTO updateProduct(Long id, ProductRequestDTO productDetails);

    ProductDTO updateProduct_v2(Long id, ProductRequestDTO productDetails);

    void deleteProduct(Long id);

    void viewProduct(Long productId);

    List<ProductDTO> getFeaturedProducts();

    List<ProductDTO> getProductsByCategory(String category);

    List<ProductDTO> getRecentlyViewedProducts();

    Customer assertAdmin();

    void isProductCreator(Long productId, Customer customer);

    List<ProductDTO> searchProductsByName(String partialName);

    List<ProductDTO> getRecommendations(Long id);

    List<ImageDTO> getImagesByProductId(Long productId);

    void addImageToProduct(Long productId, ImageDTO imageDTO);

    void addImageToProduct_v2(Long productId, ImageDTO imageDTO);

    void changeMainImageOfProduct(Long productId, ImageDTO imageDTO);

    GenericResponseDTO toggleFavorite(Long productId);

    List<Long> getFavorites();

    void removeProductSecondaryImages(Long productId);





    List<ProductDTO> getProductsByPriceRange(Double minPrice, Double maxPrice);
    List<ProductDTO> getProductsByCategories(List<String> categories);
    List<ProductDTO> getProductsInStock();
    List<ProductDTO> getProductsAbovePrice(Double price);
}
