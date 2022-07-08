package com.anthony.product.controller;

import com.anthony.product.model.dto.LibraryBookDto;
import com.anthony.product.model.dto.LibraryDto;
import com.anthony.product.model.dto.ProductDto;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.service.LibraryService;
import com.anthony.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public record LibraryController(LibraryService libraryService) {

    @GetMapping(path = "/{id}")
    public LibraryEntity getProduct(@PathVariable long id) {
        return libraryService.getLibrary(id);
    }

    @PostMapping
    public LibraryEntity addLibrary(@Valid @RequestBody LibraryDto input) {
        return libraryService.addLibrary(input);
    }

    @PutMapping
    public LibraryEntity updateLibrary(@Valid @RequestBody LibraryBookDto input){
        return libraryService.updateLibrary(input);
    }
}
