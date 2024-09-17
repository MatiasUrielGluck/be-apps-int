package com.uade.beappsint.repository;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductGraphRepository extends Neo4jRepository<Product, String> {

    @Query("MATCH (p:Product) RETURN p")
    List<Product> findAllProductsInGraph();

    @Query("MATCH (p:Product {code: $code}) RETURN p")
    Product findProductByCodeInGraph(String code);

    @Query("MATCH (p:Product {name: $name}) RETURN p")
    Product findProductByNameInGraph(String name);

    @Query("CREATE (p:Product {code: $code, name: $name, description: $description, stock: $stock, price: $price, category: $category}) RETURN p")
    Product createProductInGraph(String code, String name, String description, int stock, double price, String category);

    @Query("MATCH (p:Product {code: $code}) " +
            "SET p.name = $name, p.description = $description, p.stock = $stock, p.price = $price, p.category = $category " +
            "RETURN p")
    Product updateProductInGraph(String code, String name, String description, int stock, double price, String category);

    @Query("MATCH (p:Product {code: $code}) DELETE p")
    void deleteProductInGraph(String code);

    @Query("MATCH (p:Product) WHERE p.highlighted = true RETURN p")
    List<Product> findHighlightedProductsInGraph();

    @Query("MATCH (p:Product) RETURN DISTINCT p.category AS category, collect(p) AS products")
    List<Product> findProductsGroupedByCategoryInGraph();

}
