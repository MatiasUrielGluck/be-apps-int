package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Product entities.
 * Extends CrudRepository to provide CRUD operations for Product entities.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    /**
     * Finds all Product entities.
     *
     * @return a list of all Product entities.
     */
    List<Product> findAll();

    /**
     * Finds Product entities by their category.
     *
     * @param category the category of the products.
     * @return a list of Product entities in the specified category.
     */
    List<Product> findByCategory(String category);

    /**
     * Finds the top 10 Product entities ordered by the number of views in descending order.
     *
     * @return a list of the top 10 most viewed Product entities.
     */
    List<Product> findTop10ByOrderByViewsDesc();
}