package com.enoca.ecommerce.domain.product.web;

import com.enoca.ecommerce.domain.product.api.ProductDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String sku;
    private BigDecimal price;
    private int stock;

    public ProductDto toDto() {
        return ProductDto.builder()
                .name(name)
                .sku(sku)
                .price(price)
                .stock(stock)
                .build();
    }
}
