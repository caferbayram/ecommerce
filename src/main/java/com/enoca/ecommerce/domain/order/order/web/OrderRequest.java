package com.enoca.ecommerce.domain.order.order.web;

import com.enoca.ecommerce.domain.order.order.api.OrderDto;
import lombok.Data;

@Data
public class OrderRequest {
    private String code;
    private String address;

    public OrderDto toDto() {
        return OrderDto.builder()
                .code(code)
                .address(address)
                .build();
    }
}
