package com.anthony.product.service;

import com.anthony.product.component.exception.errors.UserExceptionErrors;
import com.anthony.product.component.exception.handler.ExistingElementFoundException;
import com.anthony.product.model.dto.Enum.Role;
import com.anthony.product.model.dto.UserDetailsImpl;
import com.anthony.product.model.dto.request.SignupRequest;
import com.anthony.product.model.dto.response.JwtResponse;
import com.anthony.product.model.dto.response.MessageResponse;
import com.anthony.product.model.entity.RoleEntity;
import com.anthony.product.model.entity.UserEntity;
import com.anthony.product.repository.RoleRepository;
import com.anthony.product.repository.UserRepository;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.anthony.product.component.exception.errors.UserExceptionErrors.*;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    private final MessageSourceHandler messageSource;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    public JwtResponse createToken(UserDetailsImpl userDetails, String jwt) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, jwt);
    }

    public MessageResponse register(SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername()))
            throwException(USERNAME_FOUND);

        if (userRepository.existsByEmail(signUpRequest.getEmail()))
            throwException(EMAIL_FOUND);

        // Create new user's account
        UserEntity user = new UserEntity(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();
        if (strRoles == null) {
            roles.add(getRole(Role.ROLE_USER));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> roles.add(getRole(Role.ROLE_ADMIN));
                    case "mod" -> roles.add(getRole(Role.ROLE_MODERATOR));
                    default -> roles.add(getRole(Role.ROLE_USER));
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    protected void throwException(UserExceptionErrors errors) {
        throw new ExistingElementFoundException(
                messageSource.getLocalMessage(errors.getKey()),
                messageSource.getLocalMessage(errors.getCode()));
    }

    protected RoleEntity getRole(Role role) {
        return roleRepository.findByName(role).orElseThrow(() -> new ExistingElementFoundException(
                messageSource.getLocalMessage(ROLE_NOT_FOUND.getKey()),
                messageSource.getLocalMessage(ROLE_NOT_FOUND.getCode())));
    }

}
