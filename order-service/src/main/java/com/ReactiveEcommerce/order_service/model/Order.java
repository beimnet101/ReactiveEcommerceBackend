package com.ReactiveEcommerce.order_service.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order")
public class Order {
    @Id
    private Integer orderId;
    private Integer productID;
    private Integer userID;
    private Integer quantity;
    private Integer totalPrice;
    private Date    timestamp;


}
