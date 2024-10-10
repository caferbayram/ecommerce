package com.enoca.ecommerce.domain.order.orderproduct.impl;

import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductDto;
import com.enoca.ecommerce.domain.order.orderproduct.api.OrderProductDto;
import com.enoca.ecommerce.domain.order.orderproduct.api.OrderProductService;
import com.enoca.ecommerce.domain.product.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository repository;
    private final ProductService productService;

    @Override
    @Transactional
    public void createOrderProducts(String orderId, List<CartProductDto> products) {
        var orderProducts = products.stream()
                .map(product -> toEntity(new OrderProduct(), orderId, product))
                .toList();
        repository.saveAll(orderProducts);
    }

    @Override
    public List<OrderProductDto> getAllByOrderId(String orderId) {
        return toDtoList(repository.findAllByOrderId(orderId));
    }

    @Override
    public List<OrderProductDto> getAllByOrderIds(List<String> orderIds) {
        return toDtoList(repository.findAllByOrderIdIn(orderIds));
    }

    private List<OrderProductDto> toDtoList(List<OrderProduct> orderProducts) {
        var products = productService.getAllById(orderProducts.stream()
                .map(OrderProduct::getProductId)
                .toList());
        return orderProducts.stream()
                .map(orderProduct -> OrderProductDto.builder()
                        .id(orderProduct.getId())
                        .created(orderProduct.getCreated())
                        .modified(orderProduct.getModified())
                        .orderId(orderProduct.getOrderId())
                        .quantity(orderProduct.getQuantity())
                        .price(orderProduct.getPrice())
                        .product(products.stream()
                                .filter(product -> product.getId().equals(orderProduct.getProductId()))
                                .findFirst()
                                .orElse(null))
                        .build())
                .toList();
    }

    private OrderProduct toEntity(OrderProduct orderProduct, String orderId, CartProductDto cartProduct) {
        orderProduct.setOrderId(orderId);
        orderProduct.setProductId(cartProduct.getProduct().getId());
        orderProduct.setQuantity(cartProduct.getQuantity());
        orderProduct.setPrice(cartProduct.getProduct().getPrice());
        return orderProduct;
    }
}
