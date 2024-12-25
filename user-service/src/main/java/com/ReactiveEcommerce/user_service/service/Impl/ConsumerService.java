package com.ReactiveEcommerce.user_service.service.Impl;

import com.ReactiveEcommerce.user_service.dto.ProductRequest;
import com.ReactiveEcommerce.user_service.dto.ProductResponse;
import com.ReactiveEcommerce.user_service.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final WebClient.Builder webClientBuilder;

    // Get all products
    public Flux<Product> getAllProducts() {
        return webClientBuilder.build()
                .get()
                .uri("http://ProductService/products/products/all")
                .retrieve()
                .bodyToFlux(Product.class);
    }

    // Get a product by ID
    // Get product by ID
    public Mono<Product> getProductById(Integer productId) {
        return webClientBuilder.build()
                .get()
                .uri("http://ProductService/products/{productId}", productId) // Using path variable
                .retrieve()
                .bodyToMono(Product.class);
    }

    // Search products by name
    public Flux<ProductResponse> searchProductsByName(String name) {
        return webClientBuilder.build()
                .get()
                .uri("http://ProductService/products/search?name={name}", name) // Using query parameter
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }

    // Get products by price range
    public Flux<ProductResponse> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return webClientBuilder.build()
                .get()
                .uri("http://ProductService/products/price-range?minPrice={minPrice}&maxPrice={maxPrice}", minPrice, maxPrice) // Using query parameters
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }

    // Add a new product
    public Mono<ProductResponse> addProduct(ProductRequest productRequest) {
        return webClientBuilder.build()
                .post()
                .uri("http://ProductService/products/addProduct")
                .bodyValue(productRequest) // Send the request body
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }

    // Update a product by ID
    public Mono<ProductResponse> updateProduct(Integer productId, ProductRequest productRequest) {
        return webClientBuilder.build()
                .put()
                .uri("http://ProductService/products/{productId}", productId)
                .bodyValue(productRequest) // Send the updated product data
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }

    // Delete product by ID
    public Mono<Void> deleteProductById(Integer productId) {
        return webClientBuilder.build()
                .delete()
                .uri("http://ProductService/products/{productId}", productId)
                .retrieve()
                .bodyToMono(Void.class);


    }
}