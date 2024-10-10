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

    @PutMapping("/{id}")
    public CartResponse update(@PathVariable String id, @RequestBody CartRequest request) {
        return CartResponse.toResponse(service.update(id, request.toDto()));
    }

    @PutMapping("/empty/{id}")
    public CartResponse emptyCart(@PathVariable String id) {
        return CartResponse.toResponse(service.emptyCart(id));
    }

}
