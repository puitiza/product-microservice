package com.anthony.product.controller;

import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.util.Generic.GenericError;
import com.anthony.product.util.Generic.ProductGeneric;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

import static com.anthony.product.model.templateDto.ExampleResponse.NOT_FOUND;

public interface ProductApi {

    @Operation(summary = "Find Product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GenericError.class)
                    )
            ),
            @ApiResponse(responseCode = "404", content = @Content(examples = @ExampleObject(value = NOT_FOUND)))
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductGeneric getProductById(@PathVariable long id);

    @Operation(summary = "Get list of Students in the System ", tags = "getStudents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(examples = @ExampleObject(value = NOT_FOUND)))
    })
    @GetMapping(path = "/by-name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductGeneric getProductByName(@RequestHeader(name = "accept-language", required = false) Locale locale,
                                    @Schema(example = "xiaomi") @PathVariable String name);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ProductGeneric addProduct(@Valid @RequestBody ProductDto input);
}
