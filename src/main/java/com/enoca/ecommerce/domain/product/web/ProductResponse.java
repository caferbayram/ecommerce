package com.enoca.ecommerce.domain.product.web;

import com.enoca.ecommerce.domain.product.api.ProductDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class ProductResponse {
    private String id;
    private Date created;
    private Date modified;
    private String name;
    private String sku;
    private BigDecimal price;
    private int stock;

    public static ProductResponse toResponse(ProductDto dto) {
        return ProductResponse.builder()
                .id(dto.getId())
                .created(dto.getCreated())
                .modified(dto.getModified())
                .name(dto.getName())
                .sku(dto.getSku())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();
    }
}
