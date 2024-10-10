package com.enoca.ecommerce.domain.order.order.impl;

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
@Table(name = Order.TABLE)
public class Order extends BaseEntity {
    public static final String TABLE = "orders";
    public static final String COL_CODE = "code";
    public static final String COL_TOTAL_PRICE = "total_price";
    public static final String COL_ADDRESS = "address";
    public static final String COL_CUSTOMER_ID = "customer_id";

    @Column(name = COL_CODE, nullable = false, unique = true)
    private String code;

    @Column(name = COL_TOTAL_PRICE)
    private BigDecimal totalPrice;

    @Column(name = COL_ADDRESS)
    private String address;

    @Column(name = COL_CUSTOMER_ID, nullable = false)
    private String customerId;
}
