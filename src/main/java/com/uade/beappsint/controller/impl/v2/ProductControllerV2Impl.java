package com.uade.beappsint.controller.impl.v2;

import com.uade.beappsint.controller.ProductController;
import com.uade.beappsint.dto.GenericResponseDTO;
import com.uade.beappsint.dto.ImageDTO;
import com.uade.beappsint.dto.Product.ProductRequestDTO;
import com.uade.beappsint.dto.ProductDTO;
import com.uade.beappsint.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/products")
@RequiredArgsConstructor
public class ProductControllerV2Impl implements ProductController {
    private final ProductService productService;

    @Override
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductRequestDTO product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct_v2(product));
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return null;
    }

    @Override
    public ResponseEntity<ProductDTO> getProductById(Long id) {
        return null;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productDetails) {
        return ResponseEntity.ok(productService.updateProduct_v2(id, productDetails));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> viewProduct(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts() {
        return null;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(String category) {
        return null;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getRecentlyViewedProducts() {
        return null;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> searchProductsByName(String partialName) {
        return null;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getRecommendations(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ImageDTO>> getImagesByProductId(Long productId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> changeImageOfProduct(Long productId, ImageDTO imageDTO) {
        return null;
    }

    @Override
    @PostMapping("post/{productId}/images")
    public ResponseEntity<Void> addImageToProduct(@PathVariable Long productId, @RequestBody ImageDTO imageDTO) {
        productService.addImageToProduct_v2(productId, imageDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<GenericResponseDTO> toggleFavorite(Long productId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> removeProductSecondaryImages(Long productId) {
        return null;
    }




    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDTO>> getProductsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        List<ProductDTO> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
}
