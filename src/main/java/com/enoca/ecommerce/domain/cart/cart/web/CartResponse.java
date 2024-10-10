package com.enoca.ecommerce.domain.cart.cart.web;

import com.enoca.ecommerce.domain.cart.cart.api.CartDto;
import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductDto;
import com.enoca.ecommerce.domain.customer.api.CustomerDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class CartResponse {
    private String id;
    private Date created;
    private Date modified;
    private BigDecimal totalPrice;
    private CustomerDto customer;
    private List<CartProductDto> products;

    public static CartResponse toResponse(CartDto dto) {
        return CartResponse.builder()
                .id(dto.getId())
                .created(dto.getCreated())
                .modified(dto.getModified())
                .totalPrice(dto.getTotalPrice())
                .customer(dto.getCustomer())
                .products(dto.getProducts())
                .build();
    }
}
