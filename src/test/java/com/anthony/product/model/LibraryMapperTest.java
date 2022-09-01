package com.anthony.product.model;

import com.anthony.product.model.dto.LibraryDto;
import com.anthony.product.model.entity.AddressEntity;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.model.mapper.LibraryMapper;
import com.anthony.product.service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class LibraryMapperTest {

    @Mock
    private LibraryMapper mapper;

    @Mock
    private AddressService addressService;

    @Test
    void whenAddCalledRealMethodCalled() {
        LibraryDto source = LibraryDto.builder()
                .name("library test")
                .addressId("1")
                .build();
        LibraryEntity target = new LibraryEntity();

        var result = new AddressEntity();
        result.setId(1);
        result.setLocation("calle los Rosales - only test");

        Mockito.doCallRealMethod().when(mapper).findAddressEntity(source, target, addressService);
        Mockito.when(addressService.getAddress(1L)).thenReturn(result);

        mapper.findAddressEntity(source, target, addressService);

        Mockito.verify(mapper, times(1)).findAddressEntity(source, target, addressService);
    }
}
