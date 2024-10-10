package com.enoca.ecommerce.domain.order.orderproduct.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, String> {
    List<OrderProduct> findAllByOrderId(String orderId);

    List<OrderProduct> findAllByOrderIdIn(List<String> orderIds);
}
