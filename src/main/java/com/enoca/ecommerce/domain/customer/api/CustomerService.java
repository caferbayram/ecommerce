package com.enoca.ecommerce.domain.customer.api;

public interface CustomerService {
    CustomerDto create(CustomerDto dto);

    CustomerDto getById(String customerId);
}
