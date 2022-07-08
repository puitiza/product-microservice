package com.anthony.product.repository;

import com.anthony.product.model.entity.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
    Optional<LibraryEntity> findByName(String libraryName);
}
