package com.ReactiveEcommerce.order_service.dto;

import java.time.LocalDate;

public class OrderResponse {

    private Integer orderId;
    private Integer productID;
    private Integer userID;
    private Integer quantity;
    private Integer totalPrice;
    private LocalDate timestamp;

    // Private constructor to enforce builder usage
    public OrderResponse(Builder builder) {
        this.orderId = builder.orderId;
        this.productID = builder.productID;
        this.userID = builder.userID;
        this.quantity = builder.quantity;
        this.totalPrice = builder.totalPrice;
        this.timestamp = builder.timestamp;
    }

    // Static inner Builder class
    public static class Builder {
        private Integer orderId;
        private Integer productID;
        private Integer userID;
        private Integer quantity;
        private Integer totalPrice;
        private LocalDate timestamp;

        public Builder orderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder productID(Integer productID) {
            this.productID = productID;
            return this;
        }

        public Builder userID(Integer userID) {
            this.userID = userID;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder totalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder timestamp(LocalDate timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public OrderResponse build() {
            return new OrderResponse(this);
        }
    }

    // Getters for fields
    public Integer getOrderId() {
        return orderId;
    }

    public Integer getProductID() {
        return productID;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }
}
