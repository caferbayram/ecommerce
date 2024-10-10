package com.enoca.ecommerce.domain.order.order.api;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(String cartId, OrderDto dto);

    OrderDto getOrderByCode(String code);

    List<OrderDto> getOrdersByCustomerId(String customerId);
}
