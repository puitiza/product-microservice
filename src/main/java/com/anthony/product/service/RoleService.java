package com.anthony.product.service;

import com.anthony.product.model.dto.Enum.Role;
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
        //role.setActivated(true);
        //role.setDateCreated(new Date());

        return repository.save(role);
        /*
        final Set<RolePrivilege> rolePrivileges = new HashSet<>();

        privileges.forEach(p -> {
            RolePrivilege newRolePrivilege = new RolePrivilege();
            newRolePrivilege.setRole(finalRole);
            newRolePrivilege.setPrivilege(p);
            newRolePrivilege.setActivated(true);
            newRolePrivilege.setDateCreated(new Date());

            RolePrivilege userRole = role_privilegeRepository.save(newRolePrivilege);
            rolePrivileges.add(userRole);
        });
        //update relation role to user
        finalRole.setRolePrivilege(rolePrivileges);
        role = roleRepository.save(finalRole);
        */

    }
}
