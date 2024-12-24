package com.ReactiveEcommerce.order_service.repository;

import com.ReactiveEcommerce.order_service.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {

    // Find orders by userID
    Flux<Order> findByUserID(Integer userID);

    // Find orders by productID
    Flux<Order> findByProductID(Integer productID);

    // Find an order by orderId
    Mono<Order> findByOrderId(Integer orderId);

    // Find orders by quantity greater than or equal to the given value
    Flux<Order> findByQuantityGreaterThanEqual(Integer quantity);

    // Find orders placed within a specific date range
    Flux<Order> findByTimestampBetween(Date startDate, Date endDate);
}
