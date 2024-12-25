package com.ReactiveEcommerce.user_service.controller;

import com.ReactiveEcommerce.user_service.dto.ProductRequest;
import com.ReactiveEcommerce.user_service.dto.ProductResponse;
import com.ReactiveEcommerce.user_service.model.Product;
import com.ReactiveEcommerce.user_service.service.Impl.ConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    //private final UserServiceImpl userService;
  private final ConsumerService consumerService;


    @GetMapping("/products")
    public Flux<Product> getAllProducts() {
        return consumerService.getAllProducts();
    }

// Get product by ID
@GetMapping("/product/{productId}")
public Mono<Product> getProductById(@PathVariable Integer productId) {
    return consumerService.getProductById(productId);
}

    // Search products by name
    @GetMapping("product/search")
    public Mono<ResponseEntity<List<ProductResponse>>> searchProductsByName(@RequestParam String name) {
        return consumerService.searchProductsByName(name)
                .collectList()
                .map(productList -> productList.isEmpty() ?
                        ResponseEntity.notFound().build() :
                        ResponseEntity.ok(productList));
    }

    // Get products by price range
    @GetMapping("product/price-range")
    public Mono<ResponseEntity<List<ProductResponse>>> getProductsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return consumerService.getProductsByPriceRange(minPrice, maxPrice)
                .collectList()
                .map(productList -> productList.isEmpty() ?
                        ResponseEntity.notFound().build() :
                        ResponseEntity.ok(productList));
    }

    // Add product
    @PostMapping("product/addProduct")
    public Mono<ResponseEntity<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest) {
        return consumerService.addProduct(productRequest)
                .map(savedProduct -> ResponseEntity.status(HttpStatus.CREATED).body(savedProduct))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    // Update product by ID
    @PutMapping("product/{productId}")
    public Mono<ResponseEntity<ProductResponse>> updateProduct(
            @PathVariable Integer productId,
            @RequestBody ProductRequest productRequest) {
        return consumerService.updateProduct(productId, productRequest)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // Delete product by ID
    @DeleteMapping("product/{productId}")
    public Mono<Void> deleteProductById(@PathVariable Integer productId) {
        return consumerService.deleteProductById(productId)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build())).then();
    }
}





