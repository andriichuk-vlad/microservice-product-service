package com.example_microservice.product_service.controller;

import com.example_microservice.product_service.dto.ProductDto;
import com.example_microservice.product_service.dto.ProductRequestDto;
import com.example_microservice.product_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> receiveProductById(@PathVariable Long id) {
        ProductDto response = productService.getProductById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        ProductDto response = productService.saveProduct(productRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/subtract")
    public ResponseEntity<ProductDto> subtractProduct(@PathVariable Long id, @RequestParam Integer quantity) {
        ProductDto response = productService.reduceProductQuantity(id, quantity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
