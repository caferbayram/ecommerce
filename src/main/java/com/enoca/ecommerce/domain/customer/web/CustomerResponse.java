package com.enoca.ecommerce.domain.customer.web;

import com.enoca.ecommerce.domain.customer.api.CustomerDto;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CustomerResponse {
    private String id;
    private Date created;
    private Date modified;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;

    public static CustomerResponse toResponse(CustomerDto dto) {
        return CustomerResponse.builder()
                .id(dto.getId())
                .created(dto.getCreated())
                .modified(dto.getModified())
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .build();
    }
}
