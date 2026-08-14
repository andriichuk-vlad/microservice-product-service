package com.example_microservice.product_service.service;


import com.example_microservice.product_service.dto.ProductDto;
import com.example_microservice.product_service.dto.ProductRequestDto;

public interface ProductService {
    ProductDto saveProduct(ProductRequestDto productRequestDto);
    ProductDto getProductById(Long id);

    ProductDto reduceProductQuantity(Long id, Integer quantity);
}
