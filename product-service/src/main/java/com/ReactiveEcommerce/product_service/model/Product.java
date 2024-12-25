package com.ReactiveEcommerce.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "products")
public class Product {
    @Id
    private Integer productId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;



    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product() {
    }

    public Product(Integer productId, String name, String description, Double price, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public static class ProductBuilder {
        private Integer productId;
        private String name;
        private String description;
        private Double price;
        private Integer quantity;

        public ProductBuilder productId(Integer productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder price(Double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Product build() {
            return new Product(productId, name, description, price, quantity);
        }
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }
}



