package com.ReactiveEcommerce.user_service.service.Impl;

import com.ReactiveEcommerce.user_service.dto.MessageResponseDTO;
import com.ReactiveEcommerce.user_service.dto.RefreshTokenRequestDTO;
import com.ReactiveEcommerce.user_service.dto.RegisterReq;
import com.ReactiveEcommerce.user_service.model.User;
import com.ReactiveEcommerce.user_service.repository.UserRepository;
import com.ReactiveEcommerce.user_service.security.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class TestService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public TestService(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public Mono<UserDetails> findByUsername(String username) {
        return
                userRepository.findByUsername(username).cast(UserDetails.class);
    }

    public Mono<User> save(RegisterReq registerReq) {
        User user = new User();
        user.setUsername(registerReq.getUsername());
        user.setPassword(registerReq.getPassword());
        user.setEmail(registerReq.getEmail());
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }


    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public MessageResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        try {
            // Extract claims and validate the refresh token
            Claims claims = jwtUtil.extractAllClaims(refreshToken);


            // Check if the token is expired
            if (claims.getExpiration().before(new Date())) {
               MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
               messageResponseDTO.setMessage("Refresh Token expired");
               messageResponseDTO.setStatus(false);
               return messageResponseDTO;
            }

            // Extract username (subject) from the token
            String username = claims.getSubject();
            String email = claims.getSubject();
            String newAccessToken = jwtUtil.generateToken(username, email);

            MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
            messageResponseDTO.setMessage(newAccessToken);
            messageResponseDTO.setStatus(true);
            return messageResponseDTO;
        } catch (Exception e) {
            MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
            messageResponseDTO.setMessage("Error refreshing the token");
            messageResponseDTO.setStatus(false);
            return messageResponseDTO;
        }
    }
}