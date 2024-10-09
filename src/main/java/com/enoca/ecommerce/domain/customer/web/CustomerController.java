package com.enoca.ecommerce.domain.customer.web;

import com.enoca.ecommerce.domain.customer.api.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public CustomerResponse create(@RequestBody CustomerRequest request) {
        return CustomerResponse.toResponse(service.create(request.toDto()));
    }
}
