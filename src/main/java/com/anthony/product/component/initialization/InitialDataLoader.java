package com.anthony.product.component.initialization;

import com.anthony.product.model.dto.Enum.Role;
import com.anthony.product.model.dto.request.SignupRequest;
import com.anthony.product.model.entity.RoleEntity;
import com.anthony.product.model.entity.UserEntity;
import com.anthony.product.service.RoleService;
import com.anthony.product.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.anthony.product.component.log.LoggingService.newLine;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleService roleService;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // == create initial privileges
       /* final PrivilegeAccess readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final PrivilegeAccess writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final PrivilegeAccess viewUsersPrivilege = createPrivilegeIfNotFound("VIEW_USERS_PRIVILEGE");
        final PrivilegeAccess assignUsersByToRolePrivilege = createPrivilegeIfNotFound("ASSIGN_USERS_BY_TO_ROLE_PRIVILEGE");
        final PrivilegeAccess passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
        final PrivilegeAccess manager_privilege = createPrivilegeIfNotFound("MANAGER_PRIVILEGE");
        final PrivilegeAccess director_privilege = createPrivilegeIfNotFound("DIRECTOR_PRIVILEGE");
        */

        // == create initial Role
        var role_1 = createRoleIfNotFound(Role.ROLE_ADMIN);
        var role_2 = createRoleIfNotFound(Role.ROLE_USER);
        var role_3 = createRoleIfNotFound(Role.ROLE_MODERATOR);
        var role_4 = createRoleIfNotFound(Role.ROLE_DIRECTOR);
        var role_5 = createRoleIfNotFound(Role.ROLE_MANAGER);
        var role_6 = createRoleIfNotFound(Role.ROLE_SUPPORT);

        log.debug("These are roles created by default: "
                + newLine() + role_1.toString() + newLine() + role_2.toString()
                + newLine() + role_3.toString() + newLine() + role_4.toString()
                + newLine() + role_5.toString() + newLine() + role_6.toString()
        );

        // == create initial user
        var user_1 = createUserIfNotFound(new SignupRequest("admin", "admin@gmail.com",
                "12345678", Role.ROLE_ADMIN.name())
        );
        var user_2 = createUserIfNotFound(new SignupRequest("user", "user@gmail.com",
                "12345678", Role.ROLE_USER.name())
        );
        var user_3 = createUserIfNotFound(new SignupRequest("moderator", "moderator@gmail.com",
                "12345678", Role.ROLE_MODERATOR.name())
        );

        log.debug("These are roles created by default: "
                + newLine() + user_1.toString() + newLine() + user_2.toString() + newLine() + user_3.toString()
        );

    }

    @Transactional
    RoleEntity createRoleIfNotFound(Role name) {
        var role = roleService.getRole(name);
        if (role.isEmpty()) return roleService.addRole(name);
        return role.get();
    }

    @Transactional
    UserEntity createUserIfNotFound(SignupRequest request) {
        var user = userDetailsService.getUserByUsername(request.getUsername());
        if (user.isEmpty()) return userDetailsService.addUser(request);
        return user.get();
    }
}
