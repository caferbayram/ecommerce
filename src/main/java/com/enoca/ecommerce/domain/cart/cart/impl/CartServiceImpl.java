package com.enoca.ecommerce.domain.cart.cart.impl;

import com.enoca.ecommerce.domain.cart.cart.api.CartDto;
import com.enoca.ecommerce.domain.cart.cart.api.CartService;
import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductDto;
import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductService;
import com.enoca.ecommerce.domain.customer.api.CustomerDto;
import com.enoca.ecommerce.domain.customer.api.CustomerService;
import com.enoca.ecommerce.domain.customer.api.event.CustomerCreatedEvent;
import com.enoca.ecommerce.domain.order.order.api.event.EmptyCartEvent;
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

    @Override
    @Transactional
    public CartDto getById(String id) {
        var cart = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        return toDto(repository.save(toEntity(CartDto.builder()
                .products(cartProductService.getAllByCartId(cart.getId()))
                .build(), cart)));
    }

    @Override
    @Transactional
    public CartDto update(String id, CartDto dto) {
        var cart = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        dto.setProducts(cartProductService.updateCartProdcuts(cart.getId(), dto.getProducts()));
        return toDto(repository.save(toEntity(dto, cart)));
    }

    @Override
    @Transactional
    public CartDto emptyCart(String id) {
        var cart = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        cartProductService.deleteAllByCartId(cart.getId());
        return toDto(repository.save(toEntity(CartDto.builder()
                .totalPrice(BigDecimal.ZERO)
                .build(), cart)));
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

    @EventListener
    @Transactional
    public void emptyCartEvent(EmptyCartEvent event) {
        emptyCart(event.cartId());
    }

    private BigDecimal calculateTotalPrice(List<CartProductDto> cartProducts) {
        if (cartProducts == null || cartProducts.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return cartProducts.stream()
                .map(cartProduct -> cartProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(cartProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private CartDto toDto(Cart cart) {
        var products = cartProductService.getAllByCartId(cart.getId());
        return CartDto.builder()
                .id(cart.getId())
                .created(cart.getCreated())
                .modified(cart.getModified())
                .totalPrice(calculateTotalPrice(products))
                .customer(customerService.getById(cart.getCustomerId()))
                .products(products)
                .build();
    }

    private Cart toEntity(CartDto dto, Cart cart) {
        cart.setCustomerId(dto.getCustomer() != null && StringUtils.hasText(dto.getCustomer().getId()) ? dto.getCustomer().getId() : cart.getCustomerId());
        cart.setTotalPrice(dto.getProducts() != null ? calculateTotalPrice(dto.getProducts()) : dto.getTotalPrice());
        return cart;
    }
}
