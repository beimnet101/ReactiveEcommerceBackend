package com.ReactiveEcommerce.user_service.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // Extract the subject (could be a username or service name)
            String subject = jwtUtil.extractUsername(token);

            // Check if the current authentication context is empty
            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Validate the token
                if (jwtUtil.validateToken(token, subject)) {
                    // Differentiate between user and service authentication
                    UsernamePasswordAuthenticationToken auth;
                    if ("user-service".equals(subject) || "notification-service".equals(subject) || "otp-service".equals(subject)) {
                        // For services, no authorities are needed
                        auth = new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
                    } else {
                        // For users, load specific user details if required
                        UserDetails userDetails = new User(subject, "", Collections.emptyList());
                        auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    }

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
