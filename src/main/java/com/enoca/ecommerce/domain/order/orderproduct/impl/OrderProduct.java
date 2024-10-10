package com.enoca.ecommerce.domain.order.orderproduct.impl;

import com.enoca.ecommerce.library.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = OrderProduct.TABLE)
public class OrderProduct extends BaseEntity {
    public static final String TABLE = "order_product";
    public static final String COL_ORDER_ID = "order_id";
    public static final String COL_PRODUCT_ID = "product_id";
    public static final String COL_PRICE = "price";
    public static final String COL_QUANTITY = "quantity";

    @Column(name = COL_ORDER_ID, nullable = false)
    private String orderId;

    @Column(name = COL_PRODUCT_ID, nullable = false)
    private String productId;

    @Column(name = COL_PRICE)
    private BigDecimal price;

    @Column(name = COL_QUANTITY)
    private int quantity;
}
