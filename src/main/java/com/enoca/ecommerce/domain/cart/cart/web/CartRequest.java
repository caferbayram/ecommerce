package com.enoca.ecommerce.domain.cart.cart.web;

import com.enoca.ecommerce.domain.cart.cart.api.CartDto;
import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductDto;
import com.enoca.ecommerce.domain.cart.cartproduct.web.CartProductRequest;
import com.enoca.ecommerce.domain.product.api.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
    private List<CartProductRequest> cartProductRequests;

    public CartDto toDto() {
        return CartDto.builder()
                .products(cartProductRequests.stream()
                        .map(req -> CartProductDto.builder()
                                .quantity(req.getQuantity())
                                .product(ProductDto.builder()
                                        .id(req.getProductId())
                                        .build())
                                .build())
                        .toList())
                .build();
    }
}
