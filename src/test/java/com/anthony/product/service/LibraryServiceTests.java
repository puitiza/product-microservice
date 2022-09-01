package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.LibraryDto;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.model.mapper.LibraryMapper;
import com.anthony.product.repository.LibraryRepository;
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
class LibraryServiceTests {

    @InjectMocks
    private LibraryService libraryService;

    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private LibraryMapper libraryMapper;
    @Mock
    private MessageSourceHandler messageSource;

    private LibraryEntity libraryEntity;

    @BeforeEach
    public void setup() {
        libraryEntity = new LibraryEntity();
        libraryEntity.setId(1);
        libraryEntity.setName("Biblioteca escolar");
    }

    @Test
    @DisplayName("JUnit test for save address method")
    void createAddressTest() {
        var item = LibraryDto.builder()
                .name("test library")
                .build();
        Mockito.when(libraryMapper.toLibraryEntity(any(LibraryDto.class), any())).thenReturn(libraryEntity);

        libraryService.addLibrary(item);

        verify(libraryRepository, times(1)).save(any(LibraryEntity.class));
    }

    @Test
    @DisplayName("JUnit test for get AddressById method")
    void getAddressByIdTest() {
        Mockito.when(libraryRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));

        var emp = libraryService.getLibrary(1L);
        assertThat(emp).isNotNull();
        assertEquals(libraryEntity.getName(), emp.getName());
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
        assertThrows(NoSuchElementFoundException.class, () -> libraryService.getLibrary(1L));
    }


}
