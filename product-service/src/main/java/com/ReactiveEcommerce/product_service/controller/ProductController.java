package com.ReactiveEcommerce.product_service.controller;

import com.ReactiveEcommerce.product_service.dto.ProductRequest;
import com.ReactiveEcommerce.product_service.dto.ProductResponse;
import com.ReactiveEcommerce.product_service.model.Product;
import com.ReactiveEcommerce.product_service.service.Impl.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products
    @GetMapping("/products/all")
    public Flux<Product> getAllProducts() {
             return  productService.getAllProducts();
    }

    // Get product by ID
    @GetMapping("/{productId}")
    public Mono<Product> getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    // Search products by name

//    @GetMapping("/search")
//    public Mono<ResponseEntity<List<ProductResponse>>> searchProductsByName(@RequestParam ProductRequest productRequest) {
//        logger.info("Received request with name: {}", productRequest.getName());
//
//        String name = productRequest.getName(); // Extract the 'name' field from the JSON body
//        Flux<ProductResponse> products = productService.searchProductsByName(name);
//
//        return products
//                .collectList()
//                .map(productList -> {
//                    if (productList.isEmpty()) {
//                        return ResponseEntity.notFound().build();
//                    } else {
//                        return ResponseEntity.ok(productList);
//                    }
//                });}

    @GetMapping("/search")
    public Mono<ResponseEntity<List<ProductResponse>>> searchProductsByName(@RequestParam String name) {
        logger.info("Received request with name: {}", name);
        Flux<ProductResponse> products = productService.searchProductsByName(name);
        return products .collectList() .map(productList -> {
            if (productList.isEmpty())
        {
            return ResponseEntity.notFound().build();
        } else {
                return ResponseEntity.ok(productList); } });
    }
    // Get products by price range
        @GetMapping("/price-range")
        public Mono<ResponseEntity<List<ProductResponse>>> getProductsByPriceRange(
                @RequestParam Double minPrice,
                @RequestParam Double maxPrice) {
            Flux<ProductResponse> products = productService.getProductsByPriceRange(minPrice, maxPrice);
            return products
                    .collectList()
                    .map(productList -> {
                        if (productList.isEmpty()) {
                            return ResponseEntity.notFound().build();
                        } else {
                            return ResponseEntity.ok(productList);
                        }
                    });
        }
    @PostMapping("/addProduct")
    public Mono<ResponseEntity<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest) {
        logger.info("Received product request: {}", productRequest);

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());

        return productService.addProduct(product)
                .map(savedProduct -> ResponseEntity.status(HttpStatus.CREATED).body(savedProduct))
                .onErrorResume(e -> {
                    logger.error("Error adding product", e);
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }




    // Update product by ID
        @PutMapping("/{productId}")
        public Mono<ResponseEntity<ProductResponse>> updateProduct(
                @PathVariable Integer productId,
                @RequestBody ProductRequest productRequest) {
            return productService.updateProduct(productId, productRequest)
                    .map(ResponseEntity::ok)
                    .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
        }

        // Delete product by ID
        @DeleteMapping("/{productId}")
        public Mono<ResponseEntity<Object>> deleteProductById(@PathVariable Integer productId) {
            return productService.deleteProductById(productId)
                    .then(Mono.just(ResponseEntity.noContent().build()))
                    .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
        }
    }



