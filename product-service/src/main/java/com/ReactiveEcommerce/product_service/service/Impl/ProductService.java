package com.ReactiveEcommerce.product_service.service.Impl;

import com.ReactiveEcommerce.product_service.dto.ProductRequest;
import com.ReactiveEcommerce.product_service.dto.ProductResponse;
import com.ReactiveEcommerce.product_service.model.Product;
import lombok.Builder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Builder
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Convert Product to ProductResponse
    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse.Builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

       //     Convert ProductRequest to Product\

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductById(Integer productId) {
        return productRepository.findByProductId(productId);

    }

    public Flux<ProductResponse> searchProductsByName(String name) {
        return productRepository.findByNameEqualsIgnoreCase(name)
                .map(this::toProductResponse);
    }

    public Flux<ProductResponse> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceGreaterThanEqual(minPrice)
                .filter(product -> product.getPrice() <= maxPrice)
                .map(this::toProductResponse);
    }

      public Mono<ProductResponse> addProduct(Product product) {
        return productRepository.save(product)
            .map(this::toProductResponse);

    }

    public Mono<ProductResponse> updateProduct(Integer productId, ProductRequest productRequest) {
        return productRepository.findByProductId(productId)
                .flatMap(existingProduct -> {
                    existingProduct.setName(productRequest.getName());
                    existingProduct.setDescription(productRequest.getDescription());
                    existingProduct.setPrice(productRequest.getPrice());
                    existingProduct.setQuantity(productRequest.getQuantity());
                    return productRepository.save(existingProduct);
                })
                .map(this::toProductResponse);
    }

    public Mono<Void> deleteProductById(Integer productId) {
        return productRepository.deleteById(productId);
    }
}
