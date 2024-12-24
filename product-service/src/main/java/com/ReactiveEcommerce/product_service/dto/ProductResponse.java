package com.ReactiveEcommerce.product_service.dto;


public class ProductResponse {
    private Integer productId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;

    // Private constructor to enforce builder usage
    private ProductResponse(Builder builder) {
        this.productId = builder.productId;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    // Static inner Builder class
    public static class Builder {
        private Integer productId;
        private String name;
        private String description;
        private Double price;
        private Integer quantity;

        public Builder productId(Integer productId) {
            this.productId = productId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public ProductResponse build() {
            return new ProductResponse(this);
        }
    }

    // Getters for fields
    public Integer getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
