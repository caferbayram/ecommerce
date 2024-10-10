package com.enoca.ecommerce.domain.cart.cartproduct.impl;

import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductDto;
import com.enoca.ecommerce.domain.cart.cartproduct.api.CartProductService;
import com.enoca.ecommerce.domain.product.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartProductServiceImpl implements CartProductService {
    private final CartProductRepository repository;
    private final ProductService productService;

    @Override
    public List<CartProductDto> getAllByCartId(String cartId) {
        return toDtoList(repository.findAllByCartId(cartId));
    }

    @Override
    @Transactional
    public List<CartProductDto> updateCartProdcuts(String cartId, List<CartProductDto> products) {
        repository.deleteAll(repository.findAllByCartId(cartId));
        return toDtoList(products.stream()
                .map(product -> repository.save(toEntity(new CartProduct(), product, cartId)))
                .toList());
    }

    private CartProduct toEntity(CartProduct cartProduct, CartProductDto dto, String cartId) {
        cartProduct.setProductId(productService.get(dto.getProduct().getId()).getId());
        cartProduct.setQuantity(dto.getQuantity());
        cartProduct.setCartId(cartId);
        return cartProduct;
    }

    private List<CartProductDto> toDtoList(List<CartProduct> cartProducts) {
        var products = productService.getAllById(cartProducts.stream()
                .map(CartProduct::getProductId)
                .toList());

        return cartProducts.stream()
                .map(cartProduct -> CartProductDto.builder()
                        .id(cartProduct.getId())
                        .created(cartProduct.getCreated())
                        .modified(cartProduct.getModified())
                        .quantity(cartProduct.getQuantity())
                        .product(products.stream()
                                .filter(product -> product.getId().equals(cartProduct.getProductId())).findFirst().orElse(null))
                        .build())
                .toList();
    }
}
