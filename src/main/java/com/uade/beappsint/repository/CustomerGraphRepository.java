package com.uade.beappsint.repository;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.entity.Product;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomerGraphRepository extends Neo4jRepository<Customer, Long> {

    Customer findByEmailInGraph(String email);

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
}
