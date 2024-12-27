package com.ReactiveEcommerce.user_service.controller;

import com.ReactiveEcommerce.user_service.dto.AuthRequest;
import com.ReactiveEcommerce.user_service.dto.AuthResponse;
import com.ReactiveEcommerce.user_service.dto.RegisterReq;
import com.ReactiveEcommerce.user_service.model.User;
import com.ReactiveEcommerce.user_service.security.JWTUtil;

import com.ReactiveEcommerce.user_service.service.Impl.TestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.ReactiveEcommerce.user_service.service.Impl.ConsumerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("")

public class AuthController {

    private final JWTUtil jwtUtil;

    private final TestService userService;
 private  final ConsumerService consumerService;

    public AuthController(JWTUtil jwtUtil, TestService userService, ConsumerService consumerService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;


        this.consumerService = consumerService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        return userService.findByUsername(authRequest.getUsername())
                .<ResponseEntity<AuthResponse>>handle((userDetails, sink) -> {
                    if (userDetails.getPassword().equals(authRequest.getPassword())) {
                        // Generate token
                        String token = jwtUtil.generateToken(authRequest.getUsername(), authRequest.getEmail());

                        // Send the login notification asynchronously
                        consumerService.sendLoginNotification(token);


                        // Return the response with token
                        sink.next(ResponseEntity.ok(new AuthResponse(token)));
                    } else {
                        sink.error(new BadCredentialsException("Invalid username or password"));
                    }
                })
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")));
    }
//    @PostMapping("/signup")
//    public Mono<ResponseEntity<String>> signup(@RequestBody RegisterReq registerReq) {
//             consumerService.sendSignUpConfirmation(registerReq);
//
//        return userService.save(registerReq)
//                .map(savedUser -> ResponseEntity.ok("User signed up successfully"));
//
//  }
   @PostMapping("/signup")
   public Mono<ResponseEntity<String>> signup(@RequestBody RegisterReq registerReq) {
    return userService.save(registerReq)
            .doOnSuccess(savedUser -> consumerService.sendSignUpConfirmation(registerReq))
            .map(savedUser -> ResponseEntity.ok("User signed up successfully"));
}



    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/protected")
    @PreAuthorize("hasRole('USER')")
    public Mono<ResponseEntity<String>> protectedEndpoint() {

        return Mono.just(ResponseEntity.ok("You have accessed a protected endpoint!"));
    }


    // Endpoint to get a user by ID
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public Mono<User> getUserById(@PathVariable String id) {
        return userService.findById(id);
    }

    // Endpoint to get all users
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('USER')")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
