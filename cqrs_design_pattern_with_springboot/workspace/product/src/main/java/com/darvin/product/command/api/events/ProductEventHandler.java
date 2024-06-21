package com.darvin.product.command.api.events;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.axonframework.eventhandling.EventHandler;
import com.darvin.product.core.entities.Product;
import com.darvin.product.core.repository.ProductRepository;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {
    private final ProductRepository repository;

    public ProductEventHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @ExceptionHandler(resultType=Exception.class)
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception {
        Product product = new Product();
        BeanUtils.copyProperties(productCreatedEvent, product);
        repository.save(product);
//        throw new Exception("Exception Occurred");
    }
}
