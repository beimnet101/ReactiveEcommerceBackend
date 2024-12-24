package com.ReactiveEcommerce.product_service.controller;

import com.ReactiveEcommerce.product_service.dto.ProductRequest;
import com.ReactiveEcommerce.product_service.dto.ProductResponse;
import com.ReactiveEcommerce.product_service.service.Impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products
    @GetMapping("/products/all")
    public Mono<ResponseEntity<Flux<ProductResponse>>> getAllProducts() {
        Flux<ProductResponse> products = productService.getAllProducts();
        return products.hasElements()
                .flatMap(hasElements -> hasElements
                        ? Mono.just(ResponseEntity.ok().body(products))
                        : Mono.just(ResponseEntity.notFound().build()));
    }

    // Get product by ID
    @GetMapping("/{productId}")
    public Mono<ResponseEntity<ProductResponse>> getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId)
                .map(product -> ResponseEntity.ok(product))  // Found the product
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())); // Not found
    }

    // Search products by name
    @GetMapping("/search")
    public Mono<ResponseEntity<Flux<ProductResponse>>> searchProductsByName(@RequestParam String name) {
        Flux<ProductResponse> products = productService.searchProductsByName(name);
        return products.hasElements()
                .flatMap(hasElements -> hasElements
                        ? Mono.just(ResponseEntity.ok().body(products))
                        : Mono.just(ResponseEntity.notFound().build()));
    }

    // Get products by price range
    @GetMapping("/price-range")
    public Mono<ResponseEntity<Flux<ProductResponse>>> getProductsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        Flux<ProductResponse> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return products.hasElements()
                .flatMap(hasElements -> hasElements
                        ? Mono.just(ResponseEntity.ok().body(products))
                        : Mono.just(ResponseEntity.notFound().build()));
    }

    // Add new product
    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    // Update product by ID
    @PutMapping("/{productId}")
    public Mono<ProductResponse> updateProduct(
            @PathVariable Integer productId,
            @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productId, productRequest);
    }

    // Delete product by ID
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductById(@PathVariable Integer productId) {
        return productService.deleteProductById(productId);
    }
}
