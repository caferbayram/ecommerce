package com.enoca.ecommerce.domain.customer.api.event;

public record CustomerCreatedEvent(
        String customerId
) {
}
