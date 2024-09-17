package com.uade.beappsint.repository;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerGraphRepository extends Neo4jRepository<Customer, Long> {
    //Customer findByEmail(String email);
    @Query("MATCH (c:Client) RETURN c")
    List<Customer> findAllClientsInGraph();

    @Query("MATCH (c:Client {username: $username}) RETURN c")
    Customer findClientByUsernameInGraph(String username);

    @Query("MATCH (c:Client) WHERE c.email = $email RETURN c")
    Customer findClientByEmailInGraph(String email);

    @Query("MATCH (c:Client {username: $username}) " +
            "MATCH (c)-[:VIO]->(p:Product) " +
            "RETURN collect(p) AS recentlyViewedProducts")
    List<Product> findRecentlyViewedProductsInGraph(String username);

    @Query("MATCH (c:Client {username: $username}) " +
            "RETURN c.purchases AS purchases")
    List<Product> findPurchasesByClientInGraph(String username);

    @Query("CREATE (c:Client {username: $username, email: $email, password: $password, firstName: $firstName, lastName: $lastName}) RETURN c")
    Customer createClientInGraph(String username, String email, String password, String firstName, String lastName);

    @Query("MATCH (c:Client {username: $username}) " +
            "SET c.email = $email, c.firstName = $firstName, c.lastName = $lastName " +
            "RETURN c")
    Customer updateClientInGraph(String username, String email, String firstName, String lastName);

    @Query("MATCH (u:User {username: $username}) " +
            "MATCH (u)-[:VIO]->(p:Product) " +
            "RETURN collect(p) AS recentlyViewedProducts")
    List<Product> findRecentlyViewedProductsByUserInGraph(String username);

    @Query("MATCH (p:Product {code: $code}) " +
            "MATCH (c:Client {username: $username}) " +
            "MERGE (c)-[s:VIO]->(p)")
    void addProductToRecentlyViewedInGraph(String username, String code);


    //Productos Favoritos del Cliente
    @Query("MATCH (c:Client {username: $username}), (p:Product {code: $productId}) CREATE (c)-[:FAVORITE]->(p)")
    void addProductToFavoritesInGraph(String username, String productId);

    @Query("MATCH (c:Client {username: $username}) " +
            "MATCH (c)-[:FAVORITE]->(p:Product) " +
            "RETURN collect(p) AS favoriteProducts")
    List<Product> findFavoriteProductsByClientInGraph(String username);

    @Query("MATCH (c:Client {username: $username})-[r:FAVORITE]->(p:Product {code: $productId}) DELETE r")
    void deleteFavoriteProduct(String username, String productId);

}
