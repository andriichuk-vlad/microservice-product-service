package com.example_microservice.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDto(
        @NotBlank
        String name,
        @NotNull
        @Positive
        BigDecimal price,
        @NotNull
        @Positive
        Integer quantity
) {
}
