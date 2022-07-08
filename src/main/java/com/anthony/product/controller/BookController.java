package com.anthony.product.controller;

import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.service.AuthorService;
import com.anthony.product.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/book")
public record BookController(BookService bookService, AuthorService authorService) {

    @GetMapping(path = "/{id}")
    public BookEntity getBook(@PathVariable long id) {
        return bookService.getBook(id);
    }

    @PostMapping
    public BookEntity addBook(@Valid @RequestBody BookEntity input) {
        return bookService.addBook(input);
    }

    @PostMapping("/authors/{authorId}/books")
    public BookEntity addBook(@PathVariable(value = "authorId") Long authorId, @RequestBody BookEntity book) {

        var author = authorService.getAuthor(authorId);
        var books = author.getBooks();

        long bookId = book.getId();
        if (bookId != 0L) {
            BookEntity _book = bookService.getBook(bookId);

            books.add(_book);
            author.setBooks(books);
            authorService.addAuthor(author);
            return _book;
        }

        //add and create new Book
        books.add(book);
        author.setBooks(books);
        authorService.addAuthor(author);

        return bookService.addBook(book);
    }
}
