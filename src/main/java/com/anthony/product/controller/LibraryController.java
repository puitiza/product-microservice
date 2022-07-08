package com.anthony.product.controller;

import com.anthony.product.model.dto.LibraryBookDto;
import com.anthony.product.model.dto.LibraryDto;
import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.service.LibraryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/library")
public record LibraryController(LibraryService libraryService) {

    @GetMapping(path = "/{id}")
    public LibraryEntity getLibrary(@PathVariable long id) {
        return libraryService.getLibrary(id);
    }

    @PostMapping
    public LibraryEntity addLibrary(@Valid @RequestBody LibraryDto input) {
        return libraryService.addLibrary(input);
    }
    @PatchMapping("/{libraryId}/books")
    public LibraryEntity addBookToLibrary(@PathVariable(value = "libraryId") Long libraryId, @RequestBody LibraryBookDto input) {
        input.setLibraryId(libraryId);
        return libraryService.updateLibrary(input);
    }
}
