package com.anthony.product.service;

import com.anthony.product.model.dto.LibraryBookDto;
import com.anthony.product.model.dto.LibraryDto;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.model.mapper.LibraryMapper;
import com.anthony.product.repository.AddressRepository;
import com.anthony.product.repository.BookRepository;
import com.anthony.product.repository.LibraryRepository;
import org.springframework.stereotype.Service;

@Service
public record LibraryService(LibraryRepository repository, AddressService addressService,
                             BookService bookService, LibraryMapper libraryMapper) {

    public LibraryEntity getLibrary(Long id) {
        var data = repository.findById(id).orElse(null);
        return data;
    }

    public LibraryEntity addLibrary(LibraryDto libraryDto) {
        var library = libraryMapper.toLibraryEntity(libraryDto,addressService);
        return repository.save(library);
    }

    public LibraryEntity updateLibrary(LibraryBookDto input){
        var library = getLibrary(input.getLibraryId());

        var book = bookService.getBook(input.getBookId());
        book.setLibrary(library);
        bookService.addBook(book);

        return getLibrary(input.getLibraryId());
    }

}
