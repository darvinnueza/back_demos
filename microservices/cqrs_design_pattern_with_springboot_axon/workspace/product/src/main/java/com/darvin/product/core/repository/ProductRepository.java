package com.darvin.product.core.repository;

import com.darvin.product.core.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> { }