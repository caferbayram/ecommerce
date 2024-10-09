package com.enoca.ecommerce.domain.product.api;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class ProductDto {
    private String id;
    private Date created;
    private Date modified;
    private String name;
    private String sku;
    private BigDecimal price;
    private int stock;
}
