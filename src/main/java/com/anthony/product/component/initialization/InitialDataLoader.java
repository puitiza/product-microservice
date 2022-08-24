package com.anthony.product.component.initialization;

import com.anthony.product.model.dto._enum.Role;
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
    private static class Constant {
        public static final String NUMBER = "12345678";
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // == create initial Role
        var role1 = createRoleIfNotFound(Role.ROLE_ADMIN);
        var role2 = createRoleIfNotFound(Role.ROLE_USER);
        var role3 = createRoleIfNotFound(Role.ROLE_MODERATOR);
        var role4 = createRoleIfNotFound(Role.ROLE_DIRECTOR);
        var role5 = createRoleIfNotFound(Role.ROLE_MANAGER);
        var role6 = createRoleIfNotFound(Role.ROLE_SUPPORT);

        log.debug("These are roles created by default: "
                + newLine() + role1.toString() + newLine() + role2.toString()
                + newLine() + role3.toString() + newLine() + role4.toString()
                + newLine() + role5.toString() + newLine() + role6.toString()
        );

        // == create initial user
        var user1 = createUserIfNotFound(new SignupRequest("admin", "admin@gmail.com",
                Constant.NUMBER, Role.ROLE_ADMIN.name())
        );
        var user2 = createUserIfNotFound(new SignupRequest("user", "user@gmail.com",
                Constant.NUMBER, Role.ROLE_USER.name())
        );
        var user3 = createUserIfNotFound(new SignupRequest("moderator", "moderator@gmail.com",
                Constant.NUMBER, Role.ROLE_MODERATOR.name())
        );

        log.debug("These are roles created by default: "
                + newLine() + user1.toString() + newLine() + user2.toString() + newLine() + user3.toString()
        );

    }

    @Transactional
    public RoleEntity createRoleIfNotFound(Role name) {
        var role = roleService.getRole(name);
        if (role.isEmpty()) return roleService.addRole(name);
        return role.get();
    }

    @Transactional
    public UserEntity createUserIfNotFound(SignupRequest request) {
        var user = userDetailsService.getUserByUsername(request.getUsername());
        if (user.isEmpty()) return userDetailsService.addUser(request);
        return user.get();
    }
}
