package com.enoca.ecommerce.domain.cart.cart.api;

import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductDto;
import com.enoca.ecommerce.domain.customer.api.CustomerDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class CartDto {
    private String id;
    private Date created;
    private Date modified;
    private BigDecimal totalPrice;
    private CustomerDto customer;
    private List<CartProductDto> products;
}
