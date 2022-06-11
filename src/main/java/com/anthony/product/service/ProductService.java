package com.anthony.product.service;

import com.anthony.product.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.model.mapper.ProductMapper;
import com.anthony.product.repository.ProductRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.anthony.product.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;

@Service
public record ProductService(ProductRepository repository, ProductMapper productMapper, MessageSource messageSource) {

    public ProductEntity getProduct(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        getLocalMessage(NO_ITEM_FOUND.getKey(), null, String.valueOf(id)))
                );
    }

    public ProductEntity getProductByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        getLocalMessage(NO_ITEM_FOUND.getKey(), null, name))
                );
    }

    public ProductEntity addProduct(ProductDto productDto) {
        var product = productMapper.toProductEntity(productDto);
        return repository.save(product);
    }

    private String getLocalMessage(String key, Locale locale, String... params) {
        return messageSource.getMessage(key,
                params,
                Locale.US);
    }
}
