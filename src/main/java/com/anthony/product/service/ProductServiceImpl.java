package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.request.ProductRequest;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.model.mapper.ProductMapper;
import com.anthony.product.repository.ProductRepository;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;

@Service
public record ProductServiceImpl(ProductRepository repository, ProductMapper productMapper,
                                 MessageSourceHandler messageSource) {

    public ProductEntity getProduct(Long id, Optional<String> locale) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), locale, String.valueOf(id)),
                                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode(), locale)
                        )
                );
    }

    public ProductEntity getProductByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NoSuchElementFoundException(
                                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), Optional.empty(), name),
                                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode(), Optional.empty())
                        )
                );
    }

    public ProductEntity addProduct(ProductRequest productDto) {
        var product = productMapper.toProductEntity(productDto);
        return repository.save(product);
    }

}
