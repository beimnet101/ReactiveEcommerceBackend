package com.ReactiveEcommerce.product_service.dto;

public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Private constructor to enforce builder usage
    private ProductRequest(Builder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    // Static inner Builder class
    public static class Builder {
        private String name;
        private String description;
        private Double price;
        private Integer quantity;

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

        public ProductRequest build() {
            return new ProductRequest(this);
        }
    }

    // Getters for fields
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
