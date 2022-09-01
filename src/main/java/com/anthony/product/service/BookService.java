package com.anthony.product.service;

import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.request.BookRequest;
import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.model.mapper.BookMapper;
import com.anthony.product.repository.BookRepository;
import com.anthony.product.util.message_source.MessageSourceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final MessageSourceHandler messageSource;
    private final BookMapper bookMapper;

    public BookEntity getBook(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementFoundException(
                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(id)),
                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
        );
    }

    public void addBook(BookEntity bookDto) {
        repository.save(bookDto);
    }

    public BookEntity addBook(BookRequest bookDto) {
        var bookEntity = bookMapper.toBookEntity(bookDto);
        return repository.save(bookEntity);
    }

    public List<BookEntity> findByLibrary(LibraryEntity library) {
        return repository.findByLibrary(library);
    }

    /**
     * Considerations when deleting book because it has relationships.
     * if the book has a library, it's necessary delete library previously.
     * book.setLibrary(null);
     * value.getBooks().remove(book);
     */
    public void deleteBook(Long id) {
        var book = getBook(id);
        new HashSet<>(book.getAuthors()).forEach(book::removeAuthor);

        Optional.ofNullable(book.getLibrary())
                .ifPresentOrElse(
                        value -> {
                            book.removeLibrary(value);
                            repository.delete(book);
                        },
                        () -> repository.delete(book)
                );
    }

}
