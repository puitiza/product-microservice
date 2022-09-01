package com.anthony.product.service;

import com.anthony.product.model.dto.request.AuthorRequest;
import com.anthony.product.model.entity.AuthorEntity;
import com.anthony.product.model.mapper.AuthorMapper;
import com.anthony.product.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTests {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorMapper authorMapper;

    private AuthorEntity authorEntity;

    @BeforeEach
    public void setup() {
        authorEntity = new AuthorEntity();
        authorEntity.setId(1);
        authorEntity.setName("Cesar Vallejo");
    }

    @Test
    @DisplayName("JUnit test for save Author method")
    void createAuthorTest() {
        var item = AuthorRequest.builder()
                .name("Cesar")
                .build();

        Mockito.when(authorMapper.toAuthorEntity(any(AuthorRequest.class))).thenReturn(authorEntity);
        authorService.addAuthor(item);

        verify(authorRepository, times(1)).save(any(AuthorEntity.class));
    }

    @Test
    @DisplayName("JUnit test for get AuthorById method")
    void getAddressByIdTest() {
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(authorEntity));

        var emp = authorService.getAuthor(1L);
        assertThat(emp).isNotNull();
        assertEquals(authorEntity.getName(), emp.getName());
    }

    @Test
    @DisplayName("JUnit test for get AuthorById method return null")
    void getAddressByIdTest_thenReturnEmpty() {
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        var emp = authorService.getAuthor(1L);
        assertThat(emp).isNull();
    }


}
