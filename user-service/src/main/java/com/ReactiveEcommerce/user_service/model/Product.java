package com.ReactiveEcommerce.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {    @Id
private Integer productId;    private String name;
    private String description;    private Double price;
    private Integer quantity;
    public Integer getProductId() {        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;    }
    public String getName() {
        return name;    }
    public void setName(String name) {
        this.name = name;    }
    public String getDescription() {
        return description;    }
    public void setDescription(String description) {
        this.description = description;    }
    public Double getPrice() {
        return price;    }
    public void setPrice(Double price) {
        this.price = price;    }
    public Integer getQuantity() {
        return quantity;    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }}
