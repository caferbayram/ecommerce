package com.enoca.ecommerce.domain.cart.cart.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByCustomerId(String customerId);
}
