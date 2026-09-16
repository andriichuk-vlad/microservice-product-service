package com.example_microservice.product_service.service;

import com.example_microservice.product_service.dto.ProductDto;
import com.example_microservice.product_service.dto.ProductRequestDto;
import com.example_microservice.product_service.exception.ProductNotFoundException;
import com.example_microservice.product_service.mapper.ProductMapper;
import com.example_microservice.product_service.model.Product;
import com.example_microservice.product_service.repository.ProductRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto saveProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = getProduct(id);
        return productMapper.toDto(product);
    }

    @Transactional
    @Override
    public ProductDto reduceProductQuantity(Long productId, Integer quantity) {
        Product product = getProduct(productId);
        int decreasedQuantity = product.getStockQuantity() - quantity;
        product.setStockQuantity(decreasedQuantity);
        productRepository.save(product); // can delete
        return productMapper.toDto(product);
    }

    private @NonNull Product getProduct(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Can't get a product by id: " + id));
    }
}
