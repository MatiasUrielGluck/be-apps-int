package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
    List<Product> findByCategory(String category);
    List<Product> findTop10ByOrderByViewsDesc();
    List<Product> findByNameContaining(String keyword);
    void deleteById(Long id);
}