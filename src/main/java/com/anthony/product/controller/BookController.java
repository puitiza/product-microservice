package com.anthony.product.controller;

import com.anthony.product.model.dto.request.BookRequest;
import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.service.BookService;
import com.anthony.product.util.generic.StringGeneric;
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
    public BookEntity addBook(@Valid @RequestBody BookRequest input) {
        return bookService.addBook(input);
    }

    @DeleteMapping(path = "/{id}")
    public StringGeneric deleteAddress(@PathVariable long id) {
        bookService.deleteBook(id);
        var response = new StringGeneric();
        response.setData("book deleted successful");
        response.setSuccess(true);
        return response;
    }
}
