package com.enoca.ecommerce.domain.order.orderproduct.api;

import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductDto;

import java.util.List;

public interface OrderProductService {
    void createOrderProducts(String orderId, List<CartProductDto> products);

    List<OrderProductDto> getAllByOrderId(String orderId);

    List<OrderProductDto> getAllByOrderIds(List<String> orderIds);
}
