package com.anthony.product.controller;

import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.service.AuthorService;
import com.anthony.product.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/book")
public record BookController(BookService bookService) {

    @GetMapping(path = "/{id}")
    public BookEntity getBook(@PathVariable long id) {
        return bookService.getBook(id);
    }

    @PostMapping
    public BookEntity addBook(@Valid @RequestBody BookEntity input) {
        return bookService.addBook(input);
    }

}
