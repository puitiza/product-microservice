package com.anthony.product.controller;

import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.dto.ProductEmployeesDto;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public record ProductController(ProductService productService) {

    @GetMapping(path = "/{id}")
    public ProductEntity getProduct(@RequestHeader(name = "accept-language", required = false) String locale,
                                    @PathVariable long id) {
        return productService.getProduct(id, Optional.ofNullable(locale));
    }

    @GetMapping(path = "/by-name/{name}")
    public ProductEntity getProductByName(@RequestHeader(name = "accept-language", required = false) Locale locale,
                                          @PathVariable String name) {
        return productService.getProductByName(name);
    }

    @PostMapping
    public ProductEntity addProduct(@Valid @RequestBody ProductDto input) {
        return productService.addProduct(input);
    }

    @PatchMapping("/{productId}/employees")
    public ProductEntity addEmployee(@PathVariable(value = "productId") Long productId, @Valid @RequestBody ProductEmployeesDto input) {
        input.setProductId(productId);
        return productService.addEmployees(input);
    }
}
