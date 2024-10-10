package com.enoca.ecommerce.domain.order.orderproduct.api;

import com.enoca.ecommerce.domain.product.api.ProductDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class OrderProductDto {
    private String id;
    private Date created;
    private Date modified;
    private ProductDto product;
    private int quantity;
    private BigDecimal price;
    private String orderId;
}
