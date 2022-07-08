package com.anthony.product.service;

import com.anthony.product.model.entity.AuthorEntity;
import com.anthony.product.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public record AuthorService(AuthorRepository repository) {

    public AuthorEntity getAuthor(Long id) {
        return repository.findById(id).orElse(null);
    }

    public AuthorEntity getAuthorByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    public AuthorEntity addAuthor(AuthorEntity authorDto) {
        return repository.save(authorDto);
    }

}
