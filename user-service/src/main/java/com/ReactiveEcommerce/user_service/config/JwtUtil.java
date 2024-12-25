package com.ReactiveEcommerce.user_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
    }

    @Value("${spring.security.jwt.expiration}")
    private long jwtExpirationInMillis;

    public String generateToken(String serviceName) {
        return Jwts.builder()
                .setSubject(serviceName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                .signWith(secretKey)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, String expectedSubject) {
        String subject = extractClaims(token).getSubject();
        return (subject.equals(expectedSubject) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
