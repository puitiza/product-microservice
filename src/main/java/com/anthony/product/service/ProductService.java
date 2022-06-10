package com.anthony.product.service;

import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.model.mapper.ProductMapper;
import com.anthony.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public record ProductService(ProductRepository repository, ProductMapper productMapper) {

    public ProductEntity getProduct(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ProductEntity getProductByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    public ProductEntity addProduct(ProductDto productDto) {
        var product = productMapper.toProductEntity(productDto);
        return repository.save(product);
    }
}
