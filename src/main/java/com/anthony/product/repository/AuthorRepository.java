package com.anthony.product.repository;

import com.anthony.product.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findByName(String nameAuthor);

    List<AuthorEntity> findAuthorEntityByBooksId(Long bookId);
}
