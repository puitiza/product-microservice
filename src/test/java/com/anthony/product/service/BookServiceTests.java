package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.request.BookRequest;
import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.model.mapper.BookMapper;
import com.anthony.product.repository.BookRepository;
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
class BookServiceTests {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private MessageSourceHandler messageSource;

    private BookEntity bookEntity;

    @BeforeEach
    public void setup() {
        bookEntity = new BookEntity();
        bookEntity.setId(1);
        bookEntity.setTitle("One day");
    }

    @Test
    @DisplayName("JUnit test for save book method")
    void createBookTest() {
        var item = BookRequest.builder()
                .name("one day")
                .build();

        Mockito.when(bookMapper.toBookEntity(any(BookRequest.class))).thenReturn(bookEntity);
        bookService.addBook(item);

        verify(bookRepository, times(1)).save(any(BookEntity.class));
    }

    @Test
    @DisplayName("JUnit test for get BookById method")
    void getBookByIdTest() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        var emp = bookService.getBook(1L);
        assertThat(emp).isNotNull();
        assertEquals(bookEntity.getTitle(), emp.getTitle());
    }

    @Test
    @DisplayName("JUnit test for get BookById method which throws exception")
    void getBookByIdTest_thenThrowsException() {
        //when
        Mockito.when(messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(1)))
                .thenReturn("Item with id {0} not found");
        Mockito.when(messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
                .thenReturn("PC-001");

        //then
        assertThrows(NoSuchElementFoundException.class, () -> bookService.getBook(1L));
    }


}
