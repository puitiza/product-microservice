package com.anthony.product.controller;

import com.anthony.product.model.dto.AuthorBooksDto;
import com.anthony.product.model.entity.AuthorEntity;
import com.anthony.product.service.AuthorService;
import com.anthony.product.util.Generic.StringGeneric;
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

    @PatchMapping("/{authorId}/books")
    public AuthorEntity addBook(@PathVariable(value = "authorId") Long authorId, @RequestBody AuthorBooksDto input) {
        input.setAuthorId(authorId);
        return authorService.addBooksToAuthor(input);
    }

    @DeleteMapping(path = "/{id}")
    public StringGeneric deleteAuthor(@PathVariable long id) {
        authorService.deleteAuthor(id);
        var response = new StringGeneric();
        response.setData("Author deleted successful");
        response.setSuccess(true);
        return response;
    }
}
