package com.darvin.product.query.api.controller;

import java.util.List;
import org.axonframework.queryhandling.QueryGateway;
import com.darvin.product.core.dtos.ProductResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.darvin.product.query.api.queries.FindProductsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<ProductResponseDto> findAllProducts() {
        FindProductsQuery query = new FindProductsQuery();
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(ProductResponseDto.class)).join();
    }
}
