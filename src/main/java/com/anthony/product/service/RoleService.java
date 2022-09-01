package com.anthony.product.service;

import com.anthony.product.model.dto._enum.Role;
import com.anthony.product.model.entity.RoleEntity;
import com.anthony.product.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record RoleService(RoleRepository repository) {

    public Optional<RoleEntity> getRole(Role name) {
        return repository.findByName(name);
    }


    public RoleEntity addRole(Role name) {
        var role = new RoleEntity();

        role.setName(name);
        return repository.save(role);

    }
}
