package com.ReactiveEcommerce.user_service.service.Impl;

import com.ReactiveEcommerce.user_service.config.JwtUtil;
import com.ReactiveEcommerce.user_service.dto.LoginRequestDTO;
import com.ReactiveEcommerce.user_service.dto.LoginResponseDTO;
import com.ReactiveEcommerce.user_service.dto.ProductRequest;
import com.ReactiveEcommerce.user_service.dto.ProductResponse;
import com.ReactiveEcommerce.user_service.exception.CredentialException;
import com.ReactiveEcommerce.user_service.feignclient.ProductServiceClient;
import com.ReactiveEcommerce.user_service.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import com.ReactiveEcommerce.user_service.model.User;
import com.ReactiveEcommerce.user_service.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ProductServiceClient productServiceClient;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;



    @Override
    public Mono<User> registerUser(User user) {
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setCreatedDate(LocalDateTime.now());
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    @Override
    public Mono<LoginResponseDTO> loginUser(LoginRequestDTO loginRequestDTO) {
        return userRepository.findByUsername(loginRequestDTO.getUsername()) // Return Mono<User>
                .flatMap(user -> {
                    if (loginRequestDTO.getPassword().equals(user.getPassword())) {
                        String jwtToken = jwtUtil.generateToken(user.getUsername());
                        return Mono.just(LoginResponseDTO.builder()
                                .message("Login success")
                                .status(true)
                                .acessToken(jwtToken)
                                .build());
                    } else {
                        return Mono.error(new CredentialException("Invalid credentials"));
                    }
                });
    }


    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productServiceClient.getAllProducts();  // Fetch all products from Product Service
    }

    @Override
    public Mono<Product> getProductById(Long id) {
        return productServiceClient.getProductById(id);
    }

    @Override
    public Mono<ProductResponse> addProduct(ProductRequest productRequest) {
        return productServiceClient.addProduct(productRequest);
    }
}
