package com.anthony.product.controller;

import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public record ProductController(ProductService productService) {

    @GetMapping(path = "/{id}")
    public ProductEntity getProduct(//@RequestHeader(name = "Accept-Language", required = false) Locale locale,
                                    @PathVariable long id) {
        return productService.getProduct(id);
    }

    @GetMapping(path = "/by-name/{name}")
    public ProductEntity getProductByName(//@RequestHeader(name = "Accept-Language", required = false) Locale locale,
                                          @PathVariable String name) {
        return productService.getProductByName(name);
    }

    @PostMapping
    public ProductEntity addProduct(@RequestBody @Valid ProductDto input) {
        return productService.addProduct(input);
    }
}
