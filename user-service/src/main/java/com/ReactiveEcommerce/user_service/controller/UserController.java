package com.ReactiveEcommerce.user_service.controller;//package com.ReactiveEcommerce.user_service.controller;
//
//import com.ReactiveEcommerce.user_service.model.Product;
//import com.ReactiveEcommerce.user_service.service.Impl.UserServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@RestController
//@RequestMapping("/user")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserServiceImpl userService;
//
//    @GetMapping("/products")
//    public Flux<Product> getAllProducts() {
//        return userService.getAllProducts();
//    }
//
//    // Get product by ID
//    @GetMapping("/products/{id}")
//    public Mono<Product> getProductById(@PathVariable Long id) {
//        return userService.getProductById(id);
//    }
//}
