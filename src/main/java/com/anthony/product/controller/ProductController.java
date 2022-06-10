package com.anthony.product.controller;

import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j(topic = "PRODUCT_CONTROLLER")
@RestController
@RequestMapping("/product")
public record ProductController(ProductService productService) {

    @GetMapping(path = "/{id}")
    public ProductEntity getProduct(//@RequestHeader(name = "Accept-Language", required = false) Locale locale,
                                    @PathVariable long id) {
        log.info("Enter this method"+ " getProduct()" );
        return productService.getProduct(id);
    }

    @GetMapping(path = "/by-name/{name}")
    public ProductEntity getProductByName(//@RequestHeader(name = "Accept-Language", required = false) Locale locale,
                                          @PathVariable String name) {
        log.info("Enter this method" + " getProduct()");
        return productService.getProductByName(name);
    }

    @PostMapping
    public ProductEntity addProduct(@RequestBody @Valid ProductDto input) {
        log.info("Enter this method"+ " addProduct()");
        return productService.addProduct(input);
    }
}
