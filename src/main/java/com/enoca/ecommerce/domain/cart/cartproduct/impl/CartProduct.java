package com.enoca.ecommerce.domain.cart.cartproduct.impl;

import com.enoca.ecommerce.library.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = CartProduct.TABLE)
public class CartProduct extends BaseEntity {
    public static final String TABLE = "cart_product";
    public static final String COL_CART_ID = "cart_id";
    public static final String COL_PRODUCT_ID = "product_id";
    public static final String COL_QUANTITY = "quantity";

    @Column(name = COL_PRODUCT_ID, nullable = false)
    private String productId;

    @Column(name = COL_QUANTITY)
    private int quantity;

    @Column(name = COL_CART_ID, nullable = false)
    private String cartId;
}
