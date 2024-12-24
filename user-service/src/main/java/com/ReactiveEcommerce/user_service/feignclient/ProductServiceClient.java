package com.ReactiveEcommerce.user_service.feignclient;

import com.ReactiveEcommerce.user_service.dto.ProductRequest;
import com.ReactiveEcommerce.user_service.dto.ProductResponse;
import com.ReactiveEcommerce.user_service.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@FeignClient(name = "product-service", url = "http://localhost:8081") //
@Component
public interface ProductServiceClient {

    @GetMapping("/products/all")
    Flux<Product> getAllProducts();  // Get all products

    @GetMapping("/products/{id}")
    Mono<Product> getProductById(@PathVariable Long id);  // Get a product by ID

    @PostMapping("/addProduct")
    Mono<ProductResponse> addProduct(@RequestBody ProductRequest productRequest);

}


