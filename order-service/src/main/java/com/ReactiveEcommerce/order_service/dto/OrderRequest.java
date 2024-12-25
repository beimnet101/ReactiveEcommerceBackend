package com.ReactiveEcommerce.order_service.dto;

import java.time.LocalDate;
import java.util.Date;

public class OrderRequest {

    private Integer productId;
    private Integer userId;
    private Integer quantity;
    private Integer totalPrice;
    private Date timestamp;

    // No-argument constructor
    public OrderRequest() {}

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

   // public LocalDate getTimestamp() {
     //   return timestamp;
    //}

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
