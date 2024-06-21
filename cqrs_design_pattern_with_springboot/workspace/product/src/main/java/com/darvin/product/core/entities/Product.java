package com.darvin.product.core.entities;

import lombok.Data;
import java.math.BigDecimal;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Data
@Entity
public class Product {

    @Id
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
