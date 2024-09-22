package com.uade.beappsint.service;

import io.jsonwebtoken.Claims;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Service interface for JWT (JSON Web Token) related operations.
 */
public interface JwtService {

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token.
     * @return the username extracted from the token.
     */
    String extractUsername(String token);

    /**
     * Extracts the claim from the provided JWT token.
     *
     * @param token the JWT token.
     * @param claimsResolver the claims resolver.
     * @param <T> the type of the claim.
     * @return the claim extracted from the token.
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the user details.
     * @return the generated JWT token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Generates a JWT token with additional claims for the given user details.
     *
     * @param extraClaims the additional claims to include in the token.
     * @param userDetails the user details.
     * @return the generated JWT token.
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * Retrieves the expiration time of the JWT token.
     *
     * @return the expiration time in milliseconds.
     */
    long getExpirationTime();

    /**
     * Builds a JWT token with additional claims, user details, and a specified expiration time.
     *
     * @param extraClaims the additional claims to include in the token.
     * @param userDetails the user details.
     * @param expiration the expiration time in milliseconds.
     * @return the built JWT token.
     */
    String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration);

    /**
     * Validates the provided JWT token against the given user details.
     *
     * @param token the JWT token.
     * @param userDetails the user details.
     * @return true if the token is valid, false otherwise.
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * Checks if the provided JWT token is expired.
     *
     * @param token the JWT token.
     * @return true if the token is expired, false otherwise.
     */
    boolean isTokenExpired(String token);

    /**
     * Extracts the expiration date from the provided JWT token.
     *
     * @param token the JWT token.
     * @return the expiration date extracted from the token.
     */
    Date extractExpiration(String token);

    /**
     * Extracts all claims from the provided JWT token.
     *
     * @param token the JWT token.
     * @return the claims extracted from the token.
     */
    Claims extractAllClaims(String token);

    /**
     * Retrieves the signing key used to sign the JWT token.
     *
     * @return the signing key.
     */
    Key getSignInKey();
}
