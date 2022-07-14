package com.anthony.product.controller;

import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.dto.ProductEmployeesDto;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.service.ProductService;
import com.anthony.product.util.Generic.ProductGeneric;
import com.anthony.product.util.Generic.StringGeneric;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.anthony.product.model.templateDto.ExampleResponse.NOT_FOUND;

@RestController
@RequestMapping(value = "/product")
public record ProductController(ProductService productService) {

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find Product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(examples = @ExampleObject(value = NOT_FOUND)))
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductGeneric getProductById(@PathVariable Long id) {
        var response = new ProductGeneric();
        response.setData(productService.getProduct(id));
        response.setSuccess(true);
        return response;
    }


    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get list of Students in the System ", tags = "getStudents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(examples = @ExampleObject(value = NOT_FOUND)))
    })
    @GetMapping(path = "/by-name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductGeneric getProductByName(@PathVariable String name) {
        var response = new ProductGeneric();
        response.setData(productService.getProductByName(name));
        response.setSuccess(true);
        return response;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductGeneric addProduct(@Valid @RequestBody ProductDto input) {
        var response = new ProductGeneric();
        response.setData(productService.addProduct(input));
        response.setSuccess(true);
        return response;
    }

    @PatchMapping("/{productId}/employees")
    public ProductEntity addEmployee(@PathVariable(value = "productId") Long productId, @Valid @RequestBody ProductEmployeesDto input) {
        input.setProductId(productId);
        return productService.addEmployees(input);
    }

    @DeleteMapping(path = "/{productId}")
    public StringGeneric deleteProduct(@PathVariable(value = "productId") Long productId) {
        productService.deleteProduct(productId);
        var response = new StringGeneric();
        response.setData("Product deleted successful");
        response.setSuccess(true);
        return response;
    }
}
