package com.anthony.product.controller;

import com.anthony.product.model.entity.AuthorEntity;
import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.service.AuthorService;
import com.anthony.product.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/author")
public record AuthorController(AuthorService authorService) {

    @GetMapping(path = "/{id}")
    public AuthorEntity getAuthor(@PathVariable long id) {
        return authorService.getAuthor(id);
    }

    @PostMapping
    public AuthorEntity addAuthor(@Valid @RequestBody AuthorEntity input) {
        return authorService.addAuthor(input);
    }
}
