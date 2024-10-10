package com.enoca.ecommerce.domain.cart.cart.web;

import com.enoca.ecommerce.domain.cart.cart.api.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService service;

    @GetMapping("/{id}")
    public CartResponse getById(@PathVariable String id) {
        return CartResponse.toResponse(service.getById(id));
    }

    @GetMapping("/customer/{customerId}")
    public CartResponse getByCustomerId(@PathVariable String customerId) {
        return CartResponse.toResponse(service.getByCustomerId(customerId));
    }

    @PutMapping("/customer/{customerId}")
    public CartResponse update(@PathVariable String customerId, @RequestBody CartRequest request) {
        return CartResponse.toResponse(service.update(customerId, request.toDto()));
    }

}
