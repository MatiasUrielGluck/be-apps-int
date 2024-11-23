package com.uade.beappsint.repository;

import com.uade.beappsint.entity.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
    List<Product> findByCategory(String category);
    List<Product> findTop10ByOrderByViewsDesc();
    List<Product> findByNameContainingIgnoreCase(String partialName);

    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.id <> :excludeProductId")
    List<Product> findRecommendationsByCategory(@Param("category") String category, @Param("excludeProductId") Long excludeProductId);

    @Query("SELECT p FROM Product p WHERE p.year BETWEEN :startYear AND :endYear AND p.id <> :excludeProductId")
    List<Product> findRecommendationsByDecade(@Param("startYear") int startYear, @Param("endYear") int endYear, @Param("excludeProductId") Long excludeProductId);

    @Query("SELECT p FROM Product p WHERE p.director = :director AND p.id <> :excludeProductId")
    List<Product> findRecommendationsByDirector(@Param("director") String director, @Param("excludeProductId") Long excludeProductId);

    @Query("SELECT i FROM Image i WHERE i.product.id = ?1")
    List<Image> findImagesByProductId(Long productId);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.imageUrl = :imageUrl WHERE p.id = :id")
    void addImageToProduct(@Param("imageUrl") String imageUrl, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM customer_recently_viewed WHERE product_id = :productId", nativeQuery = true)
    void deleteAllByProductId(@Param("productId") Long productId);






    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Product> findByCategoryIn(List<String> categories);
}
