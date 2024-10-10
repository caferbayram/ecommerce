package com.enoca.ecommerce.domain.cart.cart.impl;

import com.enoca.ecommerce.domain.cart.cart.api.CartDto;
import com.enoca.ecommerce.domain.cart.cart.api.CartService;
import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductDto;
import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductService;
import com.enoca.ecommerce.domain.customer.api.CustomerDto;
import com.enoca.ecommerce.domain.customer.api.CustomerService;
import com.enoca.ecommerce.domain.customer.api.event.CustomerCreatedEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final CustomerService customerService;
    private final CartProductService cartProductService;

    public static final String NOT_FOUND = "Cart not found with id: ";
    public static final String NOT_FOUND_CUSTOMER = "Cart not found with customer id: ";

    @Override
    public CartDto getById(String id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
    }

    @Override
    public CartDto getByCustomerId(String customerId) {
        return repository.findByCustomerId(customerId)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CUSTOMER + customerId));
    }

    @Override
    @Transactional
    public CartDto update(String customerId, CartDto dto) {
        var cart = repository.findByCustomerId(customerId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CUSTOMER + customerId));
        var cartProducts = cartProductService.updateCartProdcuts(cart.getId(), dto.getProducts());
        calculateTotalPrice(cart, cartProducts);
        return toDto(repository.save(toEntity(dto, cart)));
    }

    @EventListener
    @Transactional
    public void create(CustomerCreatedEvent event) {
        if (repository.findByCustomerId(event.customerId()).isEmpty()) {
            repository.save(toEntity(CartDto.builder()
                    .customer(CustomerDto.builder()
                            .id(event.customerId())
                            .build())
                    .build(), new Cart()));
        }
    }

    private void calculateTotalPrice(Cart cart, List<CartProductDto> cartProducts) {
        cart.setTotalPrice(cartProducts.stream()
                .map(cartProduct -> cartProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(cartProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private CartDto toDto(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .created(cart.getCreated())
                .modified(cart.getModified())
                .totalPrice(cart.getTotalPrice())
                .customer(customerService.getById(cart.getCustomerId()))
                .products(cartProductService.getAllByCartId(cart.getId()))
                .build();
    }

    private Cart toEntity(CartDto dto, Cart cart) {
        cart.setCustomerId(dto.getCustomer() != null && StringUtils.hasText(dto.getCustomer().getId()) ? dto.getCustomer().getId() : cart.getCustomerId());
        return cart;
    }
}
