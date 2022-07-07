package com.anthony.product.controller;

import com.anthony.product.model.dto.AddressDto;
import com.anthony.product.model.entity.AddressEntity;
import com.anthony.product.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/address")
public record AddressController(AddressService addressService) {

    @GetMapping(path = "/{id}")
    public AddressEntity getAddress(@PathVariable long id) {
        return addressService.getAddress(id);
    }

    @GetMapping(path = "/by-name/{name}")
    public AddressEntity getProductByName(@PathVariable String name) {
        return addressService.getAddressByLocation(name);
    }

    @PostMapping
    public AddressEntity addProduct(@Valid @RequestBody AddressDto input) {
        return addressService.addAddress(input);
    }
}
