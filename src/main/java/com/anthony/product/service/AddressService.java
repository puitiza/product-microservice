package com.anthony.product.service;

import com.anthony.product.model.dto.AddressDto;
import com.anthony.product.model.entity.AddressEntity;
import com.anthony.product.model.mapper.AddressMapper;
import com.anthony.product.model.mapper.ProductMapper;
import com.anthony.product.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public record AddressService(AddressRepository repository, AddressMapper addressMapper) {

    public AddressEntity getAddress(Long id) {
        return repository.findById(id).orElse(null);
    }

    public AddressEntity getAddressByLocation(String location) {
        return repository.findByLocation(location).orElse(null);
    }

    public AddressEntity addAddress(AddressDto addressDto) {
        var product = addressMapper.toAddressEntity(addressDto);
        return repository.save(product);
    }

}
