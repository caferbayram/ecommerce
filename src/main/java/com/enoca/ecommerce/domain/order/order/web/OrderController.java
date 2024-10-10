package com.enoca.ecommerce.domain.order.order.web;

import com.enoca.ecommerce.domain.order.order.api.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PutMapping("/{cartId}")
    public OrderResponse placeOrder(@PathVariable String cartId, @RequestBody OrderRequest request) {
        return OrderResponse.toResponse(service.placeOrder(cartId, request.toDto()));
    }

    @GetMapping("code/{code}")
    public OrderResponse getOrderByCode(@PathVariable String code) {
        return OrderResponse.toResponse(service.getOrderByCode(code));
    }

    @GetMapping("customer/{customerId}")
    public List<OrderResponse> getOrdersByCustomerId(@PathVariable String customerId) {
        return OrderResponse.toListResponse(service.getOrdersByCustomerId(customerId));
    }
}
