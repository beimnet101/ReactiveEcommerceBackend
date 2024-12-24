package com.ReactiveEcommerce.user_service.service.Impl;


import com.ReactiveEcommerce.user_service.dto.LoginRequestDTO;
import com.ReactiveEcommerce.user_service.dto.LoginResponseDTO;
import com.ReactiveEcommerce.user_service.dto.ProductRequest;
import com.ReactiveEcommerce.user_service.dto.ProductResponse;
import com.ReactiveEcommerce.user_service.model.Product;
import com.ReactiveEcommerce.user_service.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> registerUser(User user); // Registration
    Mono<LoginResponseDTO> loginUser(LoginRequestDTO loginRequestDTO);
    Mono<User> findById(Long id); // Find user by ID
    Flux<User> getAllUsers(); // Retrieve all users
    Flux<Product> getAllProducts();
    Mono<Product> getProductById(Long id);

    Mono<ProductResponse> addProduct(@RequestBody ProductRequest productRequest);

}

