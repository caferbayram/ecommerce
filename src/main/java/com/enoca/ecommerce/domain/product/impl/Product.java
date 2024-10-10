package com.enoca.ecommerce.domain.product.impl;

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
@Table(name = Product.TABLE)
public class Product extends BaseEntity {
    public static final String TABLE = "product";
    public static final String COL_NAME = "name";
    public static final String COL_SKU = "sku";
    public static final String COL_PRICE = "price";
    public static final String COL_STOCK = "stock";

    @Column(name = COL_NAME)
    private String name;

    @Column(name = COL_SKU, unique = true, nullable = false)
    private String sku;

    @Column(name = COL_PRICE)
    private BigDecimal price;

    @Column(name = COL_STOCK)
    private int stock;
}
