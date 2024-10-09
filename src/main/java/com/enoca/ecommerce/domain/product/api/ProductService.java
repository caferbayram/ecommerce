package com.enoca.ecommerce.domain.product.api;

public interface ProductService {
    ProductDto create(ProductDto dto);

    ProductDto get(String id);

    ProductDto update(String id, ProductDto dto);

    void delete(String id);
}
