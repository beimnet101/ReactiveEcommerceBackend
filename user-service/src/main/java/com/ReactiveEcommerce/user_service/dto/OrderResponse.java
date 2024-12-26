package com.ReactiveEcommerce.user_service.dto;

import lombok.Data;

import java.time.LocalDate;



@Data
public class OrderResponse {
    private Integer orderId;
    private Integer productID;
    private Integer userID;
    private Integer quantity;
    private Double totalPrice;
    private String timestamp;
}
