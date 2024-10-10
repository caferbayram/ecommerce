package com.enoca.ecommerce.domain.order.order.web;

import com.enoca.ecommerce.domain.customer.api.CustomerDto;
import com.enoca.ecommerce.domain.order.order.api.OrderDto;
import com.enoca.ecommerce.domain.order.orderproduct.api.OrderProductDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String id;
    private Date created;
    private Date modified;
    private String code;
    private String address;
    private BigDecimal totalPrice;
    private CustomerDto customer;
    private List<OrderProductDto> orderProducts;

    public static OrderResponse toResponse(OrderDto dto) {
        return OrderResponse.builder()
                .id(dto.getId())
                .created(dto.getCreated())
                .modified(dto.getModified())
                .code(dto.getCode())
                .address(dto.getAddress())
                .totalPrice(dto.getTotalPrice())
                .customer(dto.getCustomer())
                .orderProducts(dto.getOrderProducts())
                .build();
    }

    public static List<OrderResponse> toListResponse(List<OrderDto> orderDtos) {
        return orderDtos.stream()
                .map(OrderResponse::toResponse)
                .toList();
    }
}
