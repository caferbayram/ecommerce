package com.enoca.ecommerce.domain.product.web;

import com.enoca.ecommerce.domain.product.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest request) {
        return ProductResponse.toResponse(service.create(request.toDto()));
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable String id) {
        return ProductResponse.toResponse(service.get(id));
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable String id, @RequestBody ProductRequest request) {
        return ProductResponse.toResponse(service.update(id, request.toDto()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
