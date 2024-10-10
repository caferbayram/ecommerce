package com.enoca.ecommerce.domain.order.order.impl;

import com.enoca.ecommerce.domain.cart.cart.api.CartDto;
import com.enoca.ecommerce.domain.cart.cart.api.CartService;
import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductDto;
import com.enoca.ecommerce.domain.customer.api.CustomerService;
import com.enoca.ecommerce.domain.order.order.api.OrderDto;
import com.enoca.ecommerce.domain.order.order.api.OrderService;
import com.enoca.ecommerce.domain.order.order.api.event.EmptyCartEvent;
import com.enoca.ecommerce.domain.order.order.api.event.SubtractStockEvent;
import com.enoca.ecommerce.domain.order.orderproduct.api.OrderProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final CartService cartService;
    private final OrderProductService orderProductService;
    private final CustomerService customerService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public OrderDto placeOrder(String cartId, OrderDto dto) {
        var cart = cartService.getById(cartId);
        checkStock(cart.getProducts());
        dto.setCustomer(cart.getCustomer());
        dto.setTotalPrice(cart.getTotalPrice());
        var order = repository.save(toEntity(new Order(), dto));
        orderProductService.createOrderProducts(order.getId(), cart.getProducts());
        publishOrderEvent(cart);
        return toDto(order);
    }

    @Override
    public OrderDto getOrderByCode(String code) {
        return repository.findByCode(code)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with code: " + code));
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(String customerId) {
        return toDtoList(repository.findAllByCustomerId(customerId));
    }

    private void checkStock(List<CartProductDto> cartProducts) {
        cartProducts.forEach(cartProduct -> {
            if (cartProduct.getQuantity() > cartProduct.getProduct().getStock()) {
                throw new EntityNotFoundException("Product out of stock with SKU: " + cartProduct.getProduct().getSku());
            }
        });
    }

    private void publishOrderEvent(CartDto cart) {
        cart.getProducts().forEach(cartProduct -> eventPublisher
                .publishEvent(new SubtractStockEvent(cartProduct.getProduct().getId(), cartProduct.getQuantity())));
        eventPublisher
                .publishEvent(new EmptyCartEvent(cart.getId()));
    }

    private List<OrderDto> toDtoList(List<Order> orders) {
        var orderProducts = orderProductService.getAllByOrderIds(orders.stream()
                .map(Order::getId)
                .toList());
        return orders.stream()
                .map(order -> OrderDto.builder()
                        .id(order.getId())
                        .created(order.getCreated())
                        .modified(order.getModified())
                        .code(order.getCode())
                        .totalPrice(order.getTotalPrice())
                        .address(order.getAddress())
                        .customer(customerService.getById(order.getCustomerId()))
                        .orderProducts(orderProducts.stream()
                                .filter(orderProduct -> orderProduct.getOrderId().equals(order.getId()))
                                .toList())
                        .build())
                .toList();
    }

    private OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .created(order.getCreated())
                .modified(order.getModified())
                .code(order.getCode())
                .totalPrice(order.getTotalPrice())
                .address(order.getAddress())
                .customer(customerService.getById(order.getCustomerId()))
                .orderProducts(orderProductService.getAllByOrderId(order.getId()))
                .build();
    }

    private Order toEntity(Order order, OrderDto dto) {
        order.setCode(dto.getCode());
        order.setTotalPrice(dto.getTotalPrice());
        order.setAddress(dto.getAddress());
        order.setCustomerId(dto.getCustomer().getId());
        return order;
    }
}
