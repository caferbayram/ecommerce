package com.enoca.ecommerce.domain.cart.cartproduct.api;

import java.util.List;

public interface CartProductService {
    List<CartProductDto> getAllByCartId(String cartId);

    List<CartProductDto> updateCartProdcuts(String cartId, List<CartProductDto> products);
}
