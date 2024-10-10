package com.enoca.ecommerce.domain.cart.cart.api;

public interface CartService {
    CartDto getById(String id);

    CartDto getByCustomerId(String customerId);

    CartDto update(String customerId, CartDto dto);
}
