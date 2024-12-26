package com.ReactiveEcommerce.order_service.controller;

import com.ReactiveEcommerce.order_service.dto.OrderRequest;
import com.ReactiveEcommerce.order_service.dto.OrderResponse;
import com.ReactiveEcommerce.order_service.model.Order;
import com.ReactiveEcommerce.order_service.service.OrderServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private final OrderServiceImp orderService;

    public OrderController(OrderServiceImp orderService) {
        this.orderService = orderService;
    }

    // Get all orders
    @GetMapping("/orders/all")
    public Flux<Order> getAllOrders(){
        return  orderService.getAllOrders();
    }

    // Get order by ID
    @GetMapping("/{orderId}")
    public Mono<Order> getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }

    // Search orders by user ID
    @GetMapping("/search")
    public Mono<ResponseEntity<List<OrderResponse>>> searchOrdersByUserId(@RequestParam Integer userId) {
        logger.info("Received request for userId: {}", userId);

        Flux<OrderResponse> orders = orderService.getOrdersByUserId(userId);

        return orders
                .collectList()
                .map(orderList -> {
                    if (orderList.isEmpty()) {
                        return ResponseEntity.notFound().build();
                    } else {
                        return ResponseEntity.ok(orderList);
                    }
                });
    }


    // Get orders by date range
    @GetMapping("/date-range")
    public Mono<ResponseEntity<List<OrderResponse>>> getOrdersByDateRange(
            @RequestParam java.util.Date startDate,
            @RequestParam java.util.Date endDate) {
        Flux<OrderResponse> orders = orderService.getOrdersByDateRange(startDate, endDate);
        return orders
                .collectList()
                .map(orderList -> {
                    if (orderList.isEmpty()) {
                        return ResponseEntity.notFound().build();
                    } else {
                        return ResponseEntity.ok(orderList);
                    }
                });
    }

    // Create a new order
    @PostMapping("/addOrder")
    public Mono<ResponseEntity<OrderResponse>> addOrder(@RequestBody OrderRequest orderRequest) {
        logger.info("Received order request: {}", orderRequest);
        logger.info("Received order request: {}", orderRequest);

        // Print individual fields for debugging
        logger.info("totalPrice: {}", orderRequest.getTotalPrice());

        // Ensure productId is not null
        Order order = new Order();
        order.setUserID(orderRequest.getUserId());
        order.setProductID(orderRequest.getProductId());  // Ensure correct mapping
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(orderRequest.getTotalPrice());

// Optional: handle this field as needed
        order.setTimestamp(LocalDate.now());
        logger.info("Order object after setting totalPrice: {}", order);// Set current timestamp if needed

        return orderService.createOrder(order)
                .map(savedOrder -> ResponseEntity.status(HttpStatus.CREATED).body(savedOrder))
                .onErrorResume(e -> {
                    logger.error("Error adding order", e);
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }

    // Update order by ID
    @PutMapping("/{orderId}")
    public Mono<ResponseEntity<OrderResponse>> updateOrder(
            @PathVariable Integer orderId,
            @RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(orderId, orderRequest)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    // Delete order by ID
    @DeleteMapping("/{orderId}")
    public Mono<ResponseEntity<Object>> deleteOrderById(@PathVariable Integer orderId) {
        return orderService.deleteOrderById(orderId)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
