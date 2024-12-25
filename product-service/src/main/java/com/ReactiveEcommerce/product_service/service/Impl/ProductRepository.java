package com.ReactiveEcommerce.product_service.service.Impl;

import com.ReactiveEcommerce.product_service.model.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
    public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
        Flux<Product> findByNameEqualsIgnoreCase(String name);

        Flux<Product> findByDescriptionContainingIgnoreCase(String description);
        Flux<Product> findByPriceLessThanEqual(Double price);
        Flux<Product> findByPriceGreaterThanEqual(Double price);
        Flux<Product> findByQuantityGreaterThanEqual(Integer quantity);
        Flux<Product> findByQuantityLessThanEqual(Integer quantity);

        // Find product by exact name match
        Mono<Product> findByName(String name);

        Mono<Product> findByProductId(Integer productId);
    }


