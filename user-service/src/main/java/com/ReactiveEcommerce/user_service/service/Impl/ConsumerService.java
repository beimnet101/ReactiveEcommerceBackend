package com.ReactiveEcommerce.user_service.service.Impl;

import com.ReactiveEcommerce.user_service.dto.*;
import com.ReactiveEcommerce.user_service.model.Order;
import com.ReactiveEcommerce.user_service.model.Product;
import com.ReactiveEcommerce.user_service.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    private JWTUtil jwtUtil;

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

     //Search products by name

//
public Flux<ProductResponse> searchProductsByName(String name) {
    return webClientBuilder.build()
            .get()
            .uri("http://ProductService/products/search?name={name}", name) // Replace with the actual service URL
            .retrieve()
            .bodyToFlux(ProductResponse.class);}




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
    // ------------------- Order Methods -------------------

    // Get all orders
    public Flux<Order> getAllOrders() {
        return webClientBuilder.build()
                .get()
                .uri("http://OrderService/orders/orders/all")
                .retrieve()
                .bodyToFlux(Order.class);
    }

    // Get an order by ID
    public Mono<Order> getOrderById(Integer orderId) {
        return webClientBuilder.build()
                .get()
                .uri("http://OrderService/orders/{orderId}", orderId)
                .retrieve()
                .bodyToMono(Order.class);
    }
    public Flux<OrderResponse> searchOrdersByUserId(Integer userId) {
        return webClientBuilder.build()
                .get()  // Sending a GET request to the OrderService search endpoint
                .uri("http://OrderService/orders/search?userId={userId}", userId)  // Pass the userId as a query parameter
                .retrieve()
                .bodyToFlux(OrderResponse.class);  // The response will be a Flux of OrderResponse
    }

    // Get orders by date range
    public Flux<OrderResponse> getOrdersByDateRange(String startDate, String endDate) {
        return webClientBuilder.build()
                .get()
                .uri("http://OrderService/orders/date-range?startDate={startDate}&endDate={endDate}", startDate, endDate)
                .retrieve()
                .bodyToFlux(OrderResponse.class);
    }

    // Add a new order
    public Mono<OrderResponse> addOrder(OrderRequest orderRequest) {
        return webClientBuilder.build()
                .post()
                .uri("http://OrderService/orders/addOrder")
                .bodyValue(orderRequest)
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }


    // Update an order by I
    public Mono<OrderResponse> updateOrder(Integer orderId, OrderRequest orderRequest) {
        return webClientBuilder.build()
                .put()
                .uri("http://OrderService/orders/{orderId}", orderId)
                .bodyValue(orderRequest)
                .retrieve()
                .bodyToMono(OrderResponse.class);
    }

    // Delete an order by ID
    public Mono<Void> deleteOrderById(Integer orderId) {
        return webClientBuilder.build()
                .delete()
                .uri("http://OrderService/orders/{orderId}", orderId)
                .retrieve()
                .bodyToMono(Void.class);
    }
    // Send order placement notification to NotificationService
    public void sendOrderPlacementNotification( String token) {
        // Extract email from the JWT token
        String email = jwtUtil.extractEmail(token);

        // Create email request and set email
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail(email);

        // Send the email request to the NotificationService
        webClientBuilder.build()
                .post()
                .uri("http://NotificationService/notifications/send-order-notification") // URL for NotificationService endpoint
                .bodyValue(emailRequest) // Send email in the body
                .retrieve()
                .bodyToMono(Void.class) // No content expected in the response
                .block(); // Block to make it synchronous
    }


    // Send registration notification to NotificationService
    public void sendLoginNotification(AuthRequest authRequest) {


        // Create email request and set email
        EmailRequest email = new EmailRequest();
        email.setEmail(authRequest.getEmail());

        webClientBuilder.build()
                .post()
                .uri("http://NotificationService/notifications/send-login-notification")  // URL for NotificationService endpoint
                .bodyValue(email)  // Send email in the body
                .retrieve()
                .bodyToMono(Void.class)  // No content expected in the response
                .block();  // Block to make it synchronous
    }

    // Send sign-up confirmation notification to NotificationService
    public void sendSignUpConfirmation(RegisterReq registerReq) {
        EmailRequest email = new EmailRequest();
        email.setEmail(registerReq.getEmail());


         webClientBuilder.build()
                .post()
                .uri("http://NotificationService/notifications/send-signup-confirmation")  // URL for NotificationService endpoint
                .bodyValue(email)  // Send email in the body
                .retrieve()
                .bodyToMono(Void.class)  // No content expected in the response
                .block();  // Block to make it synchronous
    }
}




