package com.anthony.product.repository;

import com.anthony.product.model.dto._enum.Role;
import com.anthony.product.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(Role name);

}
