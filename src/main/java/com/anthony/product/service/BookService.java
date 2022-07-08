package com.anthony.product.service;

import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.repository.BookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record BookService(BookRepository repository) {

    public BookEntity getBook(Long id) {
        return repository.findById(id).orElse(null);
    }

    public BookEntity addBook(BookEntity bookDto) {
        return repository.save(bookDto);
    }

    public List<BookEntity> findByLibrary(LibraryEntity library){
        return repository.findByLibrary(library);
    }

}
