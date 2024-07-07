package com.darvin.product.command.api.events;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreatedEvent {

    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
