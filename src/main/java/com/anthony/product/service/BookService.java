package com.anthony.product.service;

import com.anthony.product.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.repository.BookRepository;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.anthony.product.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;

@Service
public record BookService(BookRepository repository, MessageSourceHandler messageSource) {

    public BookEntity getBook(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementFoundException(
                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(id)),
                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
        );
    }

    public BookEntity addBook(BookEntity bookDto) {
        return repository.save(bookDto);
    }

    public List<BookEntity> findByLibrary(LibraryEntity library){
        return repository.findByLibrary(library);
    }

    public void deleteBook(Long id){
        var book = getBook(id);
        if (book.getLibrary() == null){
            repository.delete(getBook(id));
        }else{
            book.setLibrary(null);
            repository.save(book);
            //you must save library with null before delete
            repository.delete(book);
        }
    }

}
