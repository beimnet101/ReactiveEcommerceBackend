package com.ReactiveEcommerce.user_service.controller;

import com.ReactiveEcommerce.user_service.dto.LoginRequestDTO;
import com.ReactiveEcommerce.user_service.dto.LoginResponseDTO;
import com.ReactiveEcommerce.user_service.exception.UserAlreadyExistException;
import com.ReactiveEcommerce.user_service.model.User;
import com.ReactiveEcommerce.user_service.service.Impl.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class AuthController {


    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to register a user
    @PostMapping("/register")
    public Mono<ResponseEntity<Object>> registerUser(@RequestBody User user) {
        return userService.registerUser(user)
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser))
                .onErrorResume(UserAlreadyExistException.class, ex ->
                        Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists with the username"))
                );
    }

    // Login method
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        return userService.loginUser(loginRequestDTO);
    }
    // Endpoint to get a user by ID
    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return userService.findById(id);
    }

    // Endpoint to get all users
    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
