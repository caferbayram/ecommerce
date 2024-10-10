package com.enoca.ecommerce.domain.customer.impl;

import com.enoca.ecommerce.domain.customer.api.CustomerDto;
import com.enoca.ecommerce.domain.customer.api.CustomerService;
import com.enoca.ecommerce.domain.customer.api.event.CustomerCreatedEvent;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public CustomerDto create(CustomerDto dto) {
        checkCustomerExist(dto.getPhoneNumber());
        var customer = repository.save(toEntity(dto, new Customer()));
        eventPublisher.publishEvent(new CustomerCreatedEvent(customer.getId()));
        return toDto(customer);
    }

    @Override
    public CustomerDto getById(String customerId) {
        return repository.findById(customerId)
                .map(this::toDto)
                .orElseThrow(() -> new EntityExistsException("Customer not found with id: " + customerId));
    }

    private void checkCustomerExist(String phoneNumber) {
        if (repository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new EntityExistsException("Customer already exists with phone number: " + phoneNumber);
        }
    }

    private CustomerDto toDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .created(customer.getCreated())
                .modified(customer.getModified())
                .name(customer.getName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();
    }

    private Customer toEntity(CustomerDto dto, Customer customer) {
        customer.setName(dto.getName());
        customer.setSurname(dto.getSurname());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setAddress(dto.getAddress());
        return customer;
    }
}
