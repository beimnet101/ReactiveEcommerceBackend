package com.ReactiveEcommerce.order_service.service;

import com.ReactiveEcommerce.order_service.dto.OrderRequest;
import com.ReactiveEcommerce.order_service.dto.OrderResponse;
import com.ReactiveEcommerce.order_service.model.Order;
import com.ReactiveEcommerce.order_service.repository.OrderRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class OrderServiceImp {

    private final OrderRepository orderRepository;

    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Convert Order to OrderResponse
    private OrderResponse toOrderResponse(Order order) {
        return new OrderResponse.Builder()
                .orderId(order.getOrderId())
                .userID(order.getUserID())
                .productID(order.getProductID())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .timestamp(order.getTimestamp())
                .build();
    }

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> getOrderById(Integer orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    public Flux<OrderResponse> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserID(userId)
                .map(this::toOrderResponse);
    }

    public Flux<OrderResponse> getOrdersByDateRange(java.util.Date startDate, java.util.Date endDate) {
        return orderRepository.findByTimestampBetween(startDate, endDate)
                .map(this::toOrderResponse);
    }

    public Mono<OrderResponse> createOrder(Order order) {
        return orderRepository.save(order)
                .map(this::toOrderResponse);
    }

    public Mono<OrderResponse> updateOrder(Integer orderId, OrderRequest orderRequest) {
        return orderRepository.findByOrderId(orderId)
                .flatMap(existingOrder -> {
                    existingOrder.setUserID(orderRequest.getUserId());
                    existingOrder.setProductID(orderRequest.getProductId());
                    existingOrder.setQuantity(orderRequest.getQuantity());
                  //  existingOrder.setTimestamp(orderRequest.getTimestamp());
                    return orderRepository.save(existingOrder);
                })
                .map(this::toOrderResponse);
    }

    public Mono<Void> deleteOrderById(Integer orderId) {
        return orderRepository.deleteById(orderId);
    }
}
