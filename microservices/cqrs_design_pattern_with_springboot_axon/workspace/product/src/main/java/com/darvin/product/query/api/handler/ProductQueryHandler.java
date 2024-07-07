package com.darvin.product.query.api.handler;

import java.util.List;
import java.util.stream.Collectors;

import com.darvin.product.core.entities.Product;
import org.springframework.stereotype.Component;
import org.axonframework.queryhandling.QueryHandler;
import com.darvin.product.core.dtos.ProductResponseDto;
import com.darvin.product.core.repository.ProductRepository;
import com.darvin.product.query.api.queries.FindProductsQuery;

@Component
public class ProductQueryHandler {

    private final ProductRepository repository;

    public ProductQueryHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public List<ProductResponseDto> findAll(FindProductsQuery query) {
        List<Product> products = repository.findAll();
        List<ProductResponseDto> response = products.stream()
                .map(product -> ProductResponseDto.builder()
                        .productId(product.getProductId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .build()).collect(Collectors.toList());
        return response;
    }
}