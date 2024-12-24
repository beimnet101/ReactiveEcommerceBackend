package com.ReactiveEcommerce.user_service.controller;


import com.ReactiveEcommerce.user_service.dto.LoginRequestDTO;
import com.ReactiveEcommerce.user_service.dto.LoginResponseDTO;
import com.ReactiveEcommerce.user_service.model.User;
import com.ReactiveEcommerce.user_service.service.Impl.UserService;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // Login method
    @PostMapping("/login")
    public Mono<LoginResponseDTO> loginUser(LoginRequestDTO loginRequestDTO) {
        return userService.loginUser(loginRequestDTO);
    }



    // Endpoint to get a user by ID
    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Endpoint to get all users
    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
