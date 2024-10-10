package com.enoca.ecommerce.domain.order.order.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByCode(String code);

    List<Order> findAllByCustomerId(String customerId);
}
