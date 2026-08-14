package com.example_microservice.product_service.mapper;

import com.example_microservice.product_service.config.MapperConfig;
import com.example_microservice.product_service.dto.ProductDto;
import com.example_microservice.product_service.dto.ProductRequestDto;
import com.example_microservice.product_service.model.Product;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toModel(ProductRequestDto productRequestDto);
}
