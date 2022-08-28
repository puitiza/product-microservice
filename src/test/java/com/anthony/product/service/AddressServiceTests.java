package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.AddressDto;
import com.anthony.product.model.entity.AddressEntity;
import com.anthony.product.model.mapper.AddressMapper;
import com.anthony.product.repository.AddressRepository;
import com.anthony.product.util.message_source.MessageSourceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddressServiceTests {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private MessageSourceHandler messageSource;

    private AddressEntity addressEntity;

    @BeforeEach
    public void setup() {
        addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setLocation("Calle Los Rosales");
    }

    @Test
    @DisplayName("JUnit test for save address method")
    void createAddressTest() {
        var item = AddressDto.builder()
                .location("local test")
                .build();

        Mockito.when(addressMapper.toAddressEntity(any(AddressDto.class))).thenReturn(addressEntity);
        addressService.addAddress(item);

        verify(addressRepository, times(1)).save(any(AddressEntity.class));
    }

    @Test
    @DisplayName("JUnit test for get AddressById method")
    void getAddressByIdTest() {
        Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(addressEntity));

        var emp = addressService.getAddress(1L);
        assertThat(emp).isNotNull();
        assertEquals(addressEntity.getLocation(), emp.getLocation());
    }

    @Test
    @DisplayName("JUnit test for get AddressById method which throws exception")
    void getAddressByIdTest_thenThrowsException() {
        //when
        Mockito.when(messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(1)))
                .thenReturn("Item with id {0} not found");
        Mockito.when(messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
                .thenReturn("PC-001");

        //then
        assertThrows(NoSuchElementFoundException.class, () -> addressService.getAddress(1L));
    }


}
