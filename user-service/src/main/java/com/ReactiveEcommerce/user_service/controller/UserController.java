package com.ReactiveEcommerce.user_service.controller;

import com.ReactiveEcommerce.user_service.dto.OrderRequest;
import com.ReactiveEcommerce.user_service.dto.OrderResponse;
import com.ReactiveEcommerce.user_service.dto.ProductRequest;
import com.ReactiveEcommerce.user_service.dto.ProductResponse;
import com.ReactiveEcommerce.user_service.model.Order;
import com.ReactiveEcommerce.user_service.model.Product;
import com.ReactiveEcommerce.user_service.security.SecurityUtil;
import com.ReactiveEcommerce.user_service.service.Impl.ConsumerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
  private  final SecurityUtil securityUtil;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/products")
    public Flux<Product> getAllProducts() {
        return consumerService.getAllProducts();
    }

// Get product by ID
@GetMapping("/product/{productId}")
public Mono<Product> getProductById(@PathVariable Integer productId) {
    return consumerService.getProductById(productId);
}


    @GetMapping("/product/search")
    public Mono<ResponseEntity<List<ProductResponse>>> searchProductsByName(@RequestParam String name) {
        return consumerService.searchProductsByName(name)
                .collectList()
                .map(productList -> {
                    if (productList.isEmpty()) {
                        return ResponseEntity.notFound().build();
                    } else {
                        return ResponseEntity.ok(productList);
                    }
                });
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
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
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
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("product/{productId}")
    public Mono<Void> deleteProductById(@PathVariable Integer productId) {
        return consumerService.deleteProductById(productId)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build())).then();
    }

        // ------------------- Order Endpoints -------------------
        @SecurityRequirement(name = "bearerAuth")
        @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/orders")
        public Flux<Order> getAllOrders() {
            return consumerService.getAllOrders();
        }
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/orders/{orderId}")
        public Mono<Order> getOrderById(@PathVariable Integer orderId) {
            return consumerService.getOrderById(orderId);
        }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/orders/date-range")
        public Flux<OrderResponse> getOrdersByDateRange(
                @RequestParam String startDate,
                @RequestParam String endDate) {
            return consumerService.getOrdersByDateRange(startDate, endDate);
        }
//    @SecurityRequirement(name = "bearerAuth")
//    @PreAuthorize("hasRole('USER')")
//        @PostMapping("/orders/createOrder")
//        public Mono<OrderResponse> addOrder(@RequestBody OrderRequest orderRequest) {
//        String token= String.valueOf(SecurityUtil.getBearerToken());
//
//
//
//                   consumerService.sendOrderPlacementNotification(token);
//            return consumerService.addOrder(orderRequest);
//
//
//        }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/orders/createOrder")
    public Mono<OrderResponse> addOrder(@RequestBody OrderRequest orderRequest) {
        // Log to check if the method is being invoked correctly
        logger.info("Starting to create order for: {}", orderRequest);

        return SecurityUtil.getBearerToken()  // Get the token reactively
                .flatMap(token -> {
                    // Log the token to verify it's being retrieved
                    logger.info("Retrieved token: {}", token);

                    // Send the order placement notification with the token
                    consumerService.sendOrderPlacementNotification(token);  // Send notification with token

                    // Proceed with order creation logic
                    return consumerService.addOrder(orderRequest)  // Continue with order creation
                            .doOnTerminate(() -> logger.info("Order creation completed for user: {}", orderRequest));
                })
                .onErrorResume(e -> {
                    // Log any errors encountered
                    logger.error("Error occurred during order creation: ", e);
                    return Mono.error(new RuntimeException("Error processing the order"));
                });
    }


    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders/search")
    public Mono<ResponseEntity<Flux<OrderResponse>>> searchOrdersByUserId(@RequestParam Integer userId) {
        Flux<OrderResponse> orders = consumerService.searchOrdersByUserId(userId);

        return orders.collectList()
                .map(orderList -> {
                    if (orderList.isEmpty()) {
                        return ResponseEntity.notFound().build();
                    } else {
                        return ResponseEntity.ok(Flux.fromIterable(orderList));
                    }
                });
    }


    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/orders/{orderId}")
        public Mono<OrderResponse> updateOrder(
                @PathVariable Integer orderId,
                @RequestBody OrderRequest orderRequest) {
            return consumerService.updateOrder(orderId, orderRequest);
        }
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/orders/{orderId}")
        public Mono<Void> deleteOrder(@PathVariable Integer orderId) {
            return consumerService.deleteOrderById(orderId);
        }
    }







