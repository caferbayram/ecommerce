package com.enoca.ecommerce.domain.cart.cart.api;

public interface CartService {
    CartDto getById(String id);

    CartDto update(String id, CartDto dto);

    CartDto emptyCart(String id);
}
