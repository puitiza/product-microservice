package com.anthony.product.controller;

import com.anthony.product.model.dto.request.ProductRequest;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.service.ProductServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public record ProductController(ProductServiceImpl productServiceImpl) {

    @GetMapping(path = "/{id}")
    public ProductEntity getProduct(@RequestHeader(name = "accept-language", required = false) String locale,
                                    @PathVariable long id) {
        return productServiceImpl.getProduct(id, Optional.ofNullable(locale));
    }

    @GetMapping(path = "/by-name/{name}")
    public ProductEntity getProductByName(@RequestHeader(name = "accept-language", required = false) Locale locale,
                                          @PathVariable String name) {
        return productServiceImpl.getProductByName(name);
    }

    @PostMapping
    public ProductEntity addProduct(@Valid @RequestBody ProductRequest input) {
        return productServiceImpl.addProduct(input);
    }
}
