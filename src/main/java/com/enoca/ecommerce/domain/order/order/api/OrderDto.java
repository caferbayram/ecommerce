package com.enoca.ecommerce.domain.order.order.api;

import com.enoca.ecommerce.domain.customer.api.CustomerDto;
import com.enoca.ecommerce.domain.order.orderproduct.api.OrderProductDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private String id;
    private Date created;
    private Date modified;
    private String code;
    private BigDecimal totalPrice;
    private String address;
    private CustomerDto customer;
    private List<OrderProductDto> orderProducts;
}
