//package com.ReactiveEcommerce.user_service;
//
//import com.ReactiveEcommerce.user_service.model.Product;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import reactor.core.publisher.Flux;
//
//@FeignClient(name="ProductService",url="http://localhost:8082/products")
//public interface ProductClient {
//    @GetMapping("products/all")
//    Flux<Product> getAllProducts();
//
//
//}