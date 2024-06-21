package com.darvin.product.core.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    private String name;
    private BigDecimal price;
    private Integer quantity;
}
