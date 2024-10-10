package com.enoca.ecommerce.domain.cart.cartproduct.api;

import com.enoca.ecommerce.domain.product.api.ProductDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CartProductDto {
    private String id;
    private Date created;
    private Date modified;
    private ProductDto product;
    private int quantity;
}
