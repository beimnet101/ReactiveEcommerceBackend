package com.ReactiveEcommerce.API_GATEWAY.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.security.jwt.secret}") String base64Secret) {
        // Decode the Base64-encoded secret and generate the SecretKey
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
    }

    @Value("${spring.security.jwt.expiration}")
    private long jwtExpirationInMillis;

    public String generateToken(String serviceName) {
        return Jwts.builder()
                .setSubject(serviceName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey) // Specify both the key and algorithm
                .compact();
    }

   public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // Use the SecretKey to validate the token
                .parseClaimsJws(token)
                .getBody();
   }

    public boolean validateToken(String token, String expectedServiceName) {
        String serviceName = extractClaims(token).getSubject();
        return (serviceName.equals(expectedServiceName) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
