package com.enoca.ecommerce.domain.cart.cartproduct.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, String> {
    List<CartProduct> findAllByCartId(String cartId);
}
