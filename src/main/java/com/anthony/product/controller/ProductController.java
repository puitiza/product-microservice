package com.anthony.product.controller;

import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.service.ProductService;
import com.anthony.product.util.Generic.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping(value = "/product")
public record ProductController(ProductService productService) implements ProductApi {

    @Override
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<ProductEntity> getProductById(long id) {
        var response = new GenericResponse<ProductEntity>();
        response.setData(productService.getProduct(id, Optional.empty()));
        response.setSuccess(true);
        return response;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<ProductEntity> getProductByName(Locale locale, String name) {
        var response = new GenericResponse<ProductEntity>();
        response.setData(productService.getProductByName(name));
        response.setSuccess(true);
        return response;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<ProductEntity> addProduct(ProductDto input) {
        var response = new GenericResponse<ProductEntity>();
        response.setData(productService.addProduct(input));
        response.setSuccess(true);
        return response;
    }
}
