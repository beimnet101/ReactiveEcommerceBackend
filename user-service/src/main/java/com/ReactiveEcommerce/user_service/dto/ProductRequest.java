package com.ReactiveEcommerce.user_service.dto;


import lombok.AllArgsConstructor;import lombok.Builder;
import lombok.Data;import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
}
