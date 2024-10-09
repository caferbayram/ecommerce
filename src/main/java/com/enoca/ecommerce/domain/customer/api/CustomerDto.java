package com.enoca.ecommerce.domain.customer.api;

import com.enoca.ecommerce.domain.order.api.OrderDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class CustomerDto {
    private String id;
    private Date created;
    private Date modified;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    private List<OrderDto> orders;
}
