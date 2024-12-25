package com.ReactiveEcommerce.user_service.service.Impl;


import com.ReactiveEcommerce.user_service.dto.LoginRequestDTO;
import com.ReactiveEcommerce.user_service.dto.LoginResponseDTO;
import com.ReactiveEcommerce.user_service.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Object> registerUser(User user); // Registration
    LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);
    Mono<User> findById(String id); // Find user by ID
    Flux<User> getAllUsers(); // Retrieve all users
//    Flux<Product> getAllProducts();
//    Mono<Product> getProductById(Long id);
//
//    Mono<ProductResponse> addProduct(@RequestBody ProductRequest productRequest);

}

