package com.anthony.product.controller;

import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.model.exception.GlobalErrorResponse;
import com.anthony.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/product")
//@Tag(name = "contact", description = "the Contact API")
public record ProductController(ProductService productService) {

    @Operation(summary = "Find Product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GlobalErrorResponse.class))})
    })
    @GetMapping(path = "/{id}", produces = "application/json")
    public ProductEntity getProduct(@RequestHeader(name = "accept-language", required = false) String locale,
                                    @PathVariable long id) {
        return productService.getProduct(id, Optional.ofNullable(locale));
    }

    @Operation(summary = "Get list of Students in the System ", tags = "getStudents")
    @ApiResponses(value = {
            @ApiResponse(responseCode =  "200", description = "Success|OK"),
            //@ApiResponse(responseCode = "401", description = "not authorized!"),
           // @ApiResponse(responseCode = "403", description = "forbidden!!!"),
            @ApiResponse(responseCode = "404", description = "not found!!!") })
    @GetMapping(path = "/by-name/{name}", produces = "application/json")
    public ProductEntity getProductByName(@RequestHeader(name = "accept-language", required = false) Locale locale,
                                          @PathVariable String name) {
        return productService.getProductByName(name);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "not found!!!")
    })
    @PostMapping(produces = "application/json")
    public ProductEntity addProduct(@Valid @RequestBody ProductDto input) {
        return productService.addProduct(input);
    }
}
