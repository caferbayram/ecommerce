package com.enoca.ecommerce.domain.customer.impl;

import com.enoca.ecommerce.domain.order.impl.Order;
import com.enoca.ecommerce.library.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = Customer.TABLE)
public class Customer extends BaseEntity {
    public static final String TABLE = "customers";
    public static final String COL_NAME = "name";
    public static final String COL_SURNAME = "surname";
    public static final String COL_EMAIL = "email";
    public static final String COL_PHONE_NUMBER = "phone_number";
    public static final String COL_ADDRESS = "address";

    @Column(name = COL_NAME)
    private String name;

    @Column(name = COL_SURNAME)
    private String surname;

    @Column(name = COL_EMAIL)
    private String email;

    @Column(name = COL_PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = COL_ADDRESS)
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
