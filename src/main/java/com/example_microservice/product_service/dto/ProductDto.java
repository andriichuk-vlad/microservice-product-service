package com.example_microservice.product_service.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        Integer stockQuantity) {
}
