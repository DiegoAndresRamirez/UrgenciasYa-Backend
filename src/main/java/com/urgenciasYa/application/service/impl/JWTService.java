package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.infrastructure.persistence.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;


@Service
public class JWTService {

    @Autowired
    UserRepository userRepository;

    private String secretkey = "";  // Secret key for signing tokens

    public JWTService() {

        try {
            // Generate a secret key using HmacSHA256
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            // Encode the key in Base64
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e); // Throw an exception if key generation fails
        }
    }

    // Method to generate a JWT token using only the username
    public String generateToken(String username) {
        return generateToken(username, new HashMap<>());
    }

    // Method to generate a JWT token using the username and additional claims
    public String generateToken(String username, Map<String, Object> additionalClaims) {
        // Retrieve the user from the repository
        UserEntity user = userRepository.findByName(username);
        // Throw an exception if the user is not found
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Extract the user's role and add it to the claims
        String roleCode = user.getRole().getCode();
        additionalClaims.put("roles", List.of(roleCode)); //  Ensure it's a list

        // Build and return the JWT token
        return Jwts.builder()
                .claims(additionalClaims)  // Add additional claims
                .subject(username)  // Set the username as the subject
                .issuedAt(new Date(System.currentTimeMillis())) // Issue date
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 300)) // Expiration date
                .signWith(getKey()) // Sign the token with the secret key
                .compact();  // Compact the token into a string
    }

    // Method to refresh the JWT token based on UserDetails
    public String refreshToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }

    // Private method to get the secret key
    private SecretKey getKey() {
        // Decode the secret key from Base64
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes); // Return the secret key
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    // Private method to extract a specific claim from the token
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);  // Apply the claim resolver
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Method to validate the JWT token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        // Check if the username matches and if the token is not expired
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Private method to check if the token has expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Private method to extract the expiration date from the token
    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration); // Extract the expiration claim
    }

}
