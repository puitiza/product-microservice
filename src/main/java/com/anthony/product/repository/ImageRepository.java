package com.anthony.product.repository;

import com.anthony.product.model.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    Optional<ImageEntity> findById(String id);

    Optional<ImageEntity> findByEmail(String email);
}
