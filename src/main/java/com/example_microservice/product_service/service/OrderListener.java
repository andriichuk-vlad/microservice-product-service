package com.example_microservice.product_service.service;

import com.example_microservice.product_service.dto.OrderDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {
    public static final String ORDER_QUEUE = "order-queue";
    private final ProductService productService;

    public OrderListener(ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(queues = ORDER_QUEUE)
    public void handleOrderCreated(OrderDto orderDto) {
        productService.reduceProductQuantity(orderDto.productId(), orderDto.quantity());
    }
}
