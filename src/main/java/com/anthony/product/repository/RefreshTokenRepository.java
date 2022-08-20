package com.anthony.product.repository;

import com.anthony.product.model.entity.RefreshTokenEntity;
import com.anthony.product.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findById(Long id);

    Optional<RefreshTokenEntity> findByToken(String token);

   // @Modifying
   // int deleteByUsers(UserEntity user);
}
