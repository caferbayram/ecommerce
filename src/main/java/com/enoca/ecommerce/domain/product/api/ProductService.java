package com.enoca.ecommerce.domain.product.api;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto dto);

    ProductDto get(String id);

    ProductDto update(String id, ProductDto dto);

    void delete(String id);

    List<ProductDto> getAllById(List<String> productIds);
}
