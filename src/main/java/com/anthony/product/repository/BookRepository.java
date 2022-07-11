package com.anthony.product.repository;

import com.anthony.product.model.entity.BookEntity;
import com.anthony.product.model.entity.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByTitle(String title);

    List<BookEntity> findByLibrary(LibraryEntity library);

    List<BookEntity> findBookEntityByAuthorsId(Long AuthorsId);

}
