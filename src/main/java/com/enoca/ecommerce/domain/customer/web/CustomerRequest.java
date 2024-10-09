package com.enoca.ecommerce.domain.customer.web;

import com.enoca.ecommerce.domain.customer.api.CustomerDto;
import lombok.Data;

@Data
public class CustomerRequest {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;

    public CustomerDto toDto() {
        return CustomerDto.builder()
                .name(name)
                .surname(surname)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }
}
