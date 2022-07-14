package com.anthony.product.controller;

import com.anthony.product.model.dto.AddressDto;
import com.anthony.product.model.entity.AddressEntity;
import com.anthony.product.service.AddressService;
import com.anthony.product.util.Generic.StringGeneric;
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
    public AddressEntity getAddressByName(@PathVariable String name) {
        return addressService.getAddressByLocation(name);
    }

    @PostMapping
    public AddressEntity addAddress(@Valid @RequestBody AddressDto input) {
        return addressService.addAddress(input);
    }

    @DeleteMapping(path = "/{id}")
    public StringGeneric deleteAddress(@PathVariable long id) {
        addressService.deleteAddress(id);
        var response = new StringGeneric();
        response.setData("Address deleted successful");
        response.setSuccess(true);
        return response;
    }
}
