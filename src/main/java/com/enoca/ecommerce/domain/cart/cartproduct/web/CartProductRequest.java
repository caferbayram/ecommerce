package com.enoca.ecommerce.domain.cart.cartproduct.web;

import lombok.Data;

@Data
public class CartProductRequest {
    private String productId;
    private int quantity;
}
