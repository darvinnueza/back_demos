package com.darvin.product.core.dtos;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;

@Data @Builder
public class ProductResponseDto {

    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
