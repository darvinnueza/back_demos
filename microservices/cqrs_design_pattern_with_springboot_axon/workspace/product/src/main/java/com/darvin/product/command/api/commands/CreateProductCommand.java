package com.darvin.product.command.api.commands;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateProductCommand {
    @TargetAggregateIdentifier
    private final String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
