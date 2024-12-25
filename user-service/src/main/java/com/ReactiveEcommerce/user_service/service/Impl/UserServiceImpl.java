package com.ReactiveEcommerce.user_service.service.Impl;


import com.ReactiveEcommerce.user_service.dto.LoginRequestDTO;
import com.ReactiveEcommerce.user_service.dto.LoginResponseDTO;
import com.ReactiveEcommerce.user_service.exception.CredentialException;
import com.ReactiveEcommerce.user_service.exception.UserAlreadyExistException;
import com.ReactiveEcommerce.user_service.model.User;
import com.ReactiveEcommerce.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//    private final ProductServiceClient productServiceClient;
    private final UserRepository userRepository;



    @Override
    public Mono<Object> registerUser(User user) {
        // Check if the user already exists
        return userRepository.findByUsername(user.getUsername())
                .flatMap(existingUser -> {
                    // If user exists, throw an exception
                    return Mono.error(new UserAlreadyExistException("User already exists with username: " + user.getUsername()));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    // If no user exists, save the new user
                    user.setCreatedDate(LocalDateTime.now());
                    user.setRole(User.Role.USER);  // Set default role as USER
                    return userRepository.save(user);
                }));
    }


    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername()).block(); // Synchronous call
        if (user != null && loginRequestDTO.getPassword().equals(user.getPassword())) {
            return new LoginResponseDTO(true, "Login success", null);  // Return success if credentials match
        } else {
            throw new CredentialException("Invalid credentials");  // Throw exception if credentials don't match
        }
    }





    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @Override
//    public Flux<Product> getAllProducts() {
//        return productServiceClient.getAllProducts();  // Fetch all products from Product Service
//    }
//
//    @Override
//    public Mono<Product> getProductById(Long id) {
//        return productServiceClient.getProductById(id);
//    }
//
//    @Override
//    public Mono<ProductResponse> addProduct(ProductRequest productRequest) {
//        return productServiceClient.addProduct(productRequest);
//    }
}
