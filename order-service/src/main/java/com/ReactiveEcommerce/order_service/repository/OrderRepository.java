package com.ReactiveEcommerce.order_service.repository;

import com.ReactiveEcommerce.order_service.model.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {

    // Find orders by user ID
    Flux<Order> findByUserID(Integer userId);

    // Find orders by product ID
    Flux<Order> findByProductID(Integer productId);

    // Find orders by quantity greater than or equal to a specified amount
    Flux<Order> findByQuantityGreaterThanEqual(Integer quantity);

    // Find orders placed within a date range
    Flux<Order> findByTimestampBetween(java.util.Date startDate, java.util.Date endDate);

    // Find order by order ID
    Mono<Order> findByOrderId(Integer orderId);
}
