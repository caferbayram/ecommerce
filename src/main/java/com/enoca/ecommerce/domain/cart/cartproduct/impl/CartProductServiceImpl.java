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
        return toDtoList(repository.saveAll(toEntityList(products, cartId)));
    }

    @Override
    @Transactional
    public void deleteAllByCartId(String cartId) {
        repository.deleteAll(repository.findAllByCartId(cartId));
    }

    private List<CartProduct> toEntityList(List<CartProductDto> cartProducts, String cartId) {
        var products = productService.getAllById(cartProducts.stream()
                .map(cartProduct -> cartProduct.getProduct().getId())
                .toList());
        return cartProducts.stream()
                .map(product -> {
                    var cartProduct = new CartProduct();
                    cartProduct.setProductId(products.stream()
                            .filter(p -> p.getId().equals(product.getProduct().getId()))
                            .findFirst()
                            .orElseThrow()
                            .getId());
                    cartProduct.setQuantity(product.getQuantity());
                    cartProduct.setCartId(cartId);
                    return cartProduct;
                })
                .toList();
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
