package com.enoca.ecommerce.domain.product.impl;

import com.enoca.ecommerce.domain.product.api.ProductDto;
import com.enoca.ecommerce.domain.product.api.ProductService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public static final String NOT_FOUND = "Product not found with id: ";

    @Override
    @Transactional
    public ProductDto create(ProductDto dto) {
        checkProductExist(dto.getSku());
        return toDto(repository.save(toEntity(dto, new Product())));
    }

    @Override
    public ProductDto get(String id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
    }

    @Override
    @Transactional
    public ProductDto update(String id, ProductDto dto) {
        var product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        if (!product.getSku().equals(dto.getSku())) {
            checkProductExist(dto.getSku());
        }
        return toDto(repository.save(toEntity(dto, product)));
    }

    @Override
    @Transactional
    public void delete(String id) {
        repository.delete(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id)));
    }

    private void checkProductExist(String sku) {
        if (repository.findBySku(sku).isPresent()) {
            throw new EntityExistsException("Product already exists with sku: " + sku);
        }
    }

    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .created(product.getCreated())
                .modified(product.getModified())
                .name(product.getName())
                .sku(product.getSku())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    private Product toEntity(ProductDto dto, Product product) {
        product.setName(dto.getName());
        product.setSku(dto.getSku());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        return product;
    }
}
