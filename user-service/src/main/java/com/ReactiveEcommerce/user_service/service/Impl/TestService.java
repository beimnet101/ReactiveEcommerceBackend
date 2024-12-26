package com.ReactiveEcommerce.user_service.service.Impl;

import com.ReactiveEcommerce.user_service.dto.RegisterReq;
import com.ReactiveEcommerce.user_service.model.User;
import com.ReactiveEcommerce.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TestService {

    private final UserRepository userRepository;

    public TestService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserDetails> findByUsername(String username) { return
            userRepository.findByUsername(username) .cast(UserDetails.class);
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
}