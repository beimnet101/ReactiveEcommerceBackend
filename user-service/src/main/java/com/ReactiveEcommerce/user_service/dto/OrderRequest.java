package com.ReactiveEcommerce.user_service.dto;

import java.util.Date;

public class OrderRequest {
    public String getEmail() {
        return Email;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

   public String Email;

    public void setEmail(String email) {
        Email = email;
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



    private Integer productId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private Integer userId;
    private Integer quantity;
    private Integer totalPrice;



}
