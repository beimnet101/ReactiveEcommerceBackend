package com.ReactiveEcommerce.user_service.repository;

import com.ReactiveEcommerce.user_service.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    // Custom query to find a user by username
    Mono<User> findByUsername(String username);

    // Optional: Add more methods as needed
}
