package com.anthony.product.service;

import com.anthony.product.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.dto.LibraryBookDto;
import com.anthony.product.model.dto.LibraryDto;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.model.mapper.LibraryMapper;
import com.anthony.product.repository.LibraryRepository;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import org.springframework.stereotype.Service;

import static com.anthony.product.exception.errors.ProductExceptionErrors.NO_ITEM_FOUND;

@Service
public record LibraryService(LibraryRepository repository, AddressService addressService, BookService bookService,
                             LibraryMapper libraryMapper, MessageSourceHandler messageSource) {

    public LibraryEntity getLibrary(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementFoundException(
                messageSource.getLocalMessage(NO_ITEM_FOUND.getKey(), String.valueOf(id)),
                messageSource.getLocalMessage(NO_ITEM_FOUND.getCode()))
        );
    }

    public LibraryEntity addLibrary(LibraryDto libraryDto) {
        var library = libraryMapper.toLibraryEntity(libraryDto, addressService);
        return repository.save(library);
    }

    public LibraryEntity updateLibrary(LibraryBookDto input) {
        var library = getLibrary(input.getLibraryId());

        var book = bookService.getBook(input.getBookId());
        book.setLibrary(library);
        bookService.addBook(book);

        return getLibrary(input.getLibraryId());
    }

    public void deleteLibrary(Long id){
        repository.delete(getLibrary(id));
    }


}
