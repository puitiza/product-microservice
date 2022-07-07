package com.anthony.product.service;

import com.anthony.product.model.dto.LibraryDto;
import com.anthony.product.model.entity.LibraryEntity;
import com.anthony.product.model.mapper.LibraryMapper;
import com.anthony.product.repository.AddressRepository;
import com.anthony.product.repository.LibraryRepository;
import org.springframework.stereotype.Service;

@Service
public record LibraryService(LibraryRepository repository, AddressRepository addressRepository, LibraryMapper libraryMapper) {

    public LibraryEntity getLibrary(Long id) {
        return repository.findById(id).orElse(null);
    }

    public LibraryEntity addProduct(LibraryDto libraryDto) {
        var library = libraryMapper.toLibraryEntity(libraryDto,addressRepository);
        return repository.save(library);
    }

}
