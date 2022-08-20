package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.AddressDto;
import com.anthony.product.model.entity.AddressEntity;
import com.anthony.product.model.mapper.AddressMapper;
import com.anthony.product.repository.AddressRepository;
import com.anthony.product.repository.LibraryRepository;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;

@Service
public record AddressService(AddressRepository repository, LibraryRepository libraryRepository,
                             AddressMapper addressMapper, MessageSourceHandler messageSource) {

    public AddressEntity getAddress(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementFoundException(
                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(id)),
                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
        );
    }

    public AddressEntity getAddressByLocation(String location) {
        return repository.findByLocation(location).orElse(null);
    }

    public AddressEntity addAddress(AddressDto addressDto) {
        var product = addressMapper.toAddressEntity(addressDto);
        return repository.save(product);
    }

    /**
     * Considerations when deleting Address because it has relationships (Library).
     * It's necessary remove the library previously  once it's completed,
     * It can delete the Address
     */
    public void deleteAddress(Long id) {
        var address = getAddress(id);
        Optional.ofNullable(address.getLibrary())
                .ifPresent((value) -> {
                    value.setAddress(null);
                    address.setLibrary(null);
                    libraryRepository.save(value);
                });
        repository.delete(address);
    }

}
