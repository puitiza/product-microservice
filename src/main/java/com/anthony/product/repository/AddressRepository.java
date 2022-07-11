package com.anthony.product.repository;

import com.anthony.product.model.entity.AddressEntity;
import com.anthony.product.model.entity.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Optional<AddressEntity> findByLocation(String libraryLocation);
}
