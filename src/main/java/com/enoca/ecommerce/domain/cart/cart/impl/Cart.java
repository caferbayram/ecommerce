package com.enoca.ecommerce.domain.cart.cart.impl;

import com.enoca.ecommerce.library.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = Cart.TABLE)
public class Cart extends BaseEntity {
    public static final String TABLE = "carts";
    public static final String COL_TOTAL_PRICE = "total_price";
    public static final String COL_CUSTOMER_ID = "customer_id";

    @Column(name = COL_TOTAL_PRICE)
    private BigDecimal totalPrice;

    @Column(name = COL_CUSTOMER_ID, nullable = false, unique = true)
    private String customerId;
}
