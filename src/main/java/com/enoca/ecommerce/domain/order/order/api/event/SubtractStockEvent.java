package com.enoca.ecommerce.domain.order.order.api.event;

public record SubtractStockEvent(
        String productId,
        int quantity
) {
}
