package com.anthony.product.service;

import com.anthony.product.model.dto.AuthorBooksDto;
import com.anthony.product.model.entity.AuthorEntity;
import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record AuthorService(AuthorRepository repository, BookService bookService) {

    public AuthorEntity getAuthor(Long id) {
        return repository.findById(id).orElse(null);
    }

    public AuthorEntity addBooksToAuthor(AuthorBooksDto request) {
        var author = getAuthor(request.getAuthorId());
        var books = author.getBooks();

        if (Optional.ofNullable(request.getBookId()).isPresent()) {
            BookEntity bookFound = bookService.getBook(request.getBookId());

            books.add(bookFound);
            author.setBooks(books);
            return addAuthor(author);
        } else {
            //save new Book
            BookEntity book = new BookEntity();
            book.setTitle(request.getTitle());
            bookService.addBook(book);

            //add and create new Book
            books.add(book);
            author.setBooks(books);
            return addAuthor(author);
        }
    }

    public AuthorEntity addAuthor(AuthorEntity authorDto) {
        return repository.save(authorDto);
    }

}
