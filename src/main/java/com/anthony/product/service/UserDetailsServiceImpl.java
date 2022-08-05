package com.anthony.product.service;

import com.anthony.product.component.exception.errors.UserExceptionErrors;
import com.anthony.product.component.exception.handler.ExistingElementFoundException;
import com.anthony.product.model.dto.Enum.Role;
import com.anthony.product.model.dto.UserDetailsImpl;
import com.anthony.product.model.dto.request.SignupRequest;
import com.anthony.product.model.dto.response.JwtResponse;
import com.anthony.product.model.dto.response.MessageResponse;
import com.anthony.product.model.entity.RefreshTokenEntity;
import com.anthony.product.model.entity.UserEntity;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.anthony.product.component.exception.errors.UserExceptionErrors.EMAIL_FOUND;
import static com.anthony.product.component.exception.errors.UserExceptionErrors.USERNAME_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    private final PasswordEncoder encoder;
    private final MessageSourceHandler messageSource;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    public JwtResponse createToken(UserDetailsImpl userDetails, String jwt, RefreshTokenEntity refreshToken) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, jwt, refreshToken.getToken());
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Create new user's account
     *
     * @param request This is the request necessary to create a new User
     * @return UserEntity created
     */
    public UserEntity addUser(SignupRequest request) {

        UserEntity user = new UserEntity(request.getUsername(), request.getEmail(), encoder.encode(request.getPassword()));

        var roleEnum = Role.valueOf(request.getRole());
        var roleEntity = roleService.getRole(roleEnum).orElse(null);
        var setRole = new HashSet<>(Collections.singletonList(roleEntity));
        user.setRoles(setRole);

        return userRepository.save(user);
    }

    public MessageResponse register(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) throwException(USERNAME_FOUND);
        if (userRepository.existsByEmail(signUpRequest.getEmail())) throwException(EMAIL_FOUND);
        addUser(signUpRequest);
        return new MessageResponse("User registered successfully!");
    }

    protected void throwException(UserExceptionErrors errors) {
        throw new ExistingElementFoundException(
                messageSource.getLocalMessage(errors.getKey()),
                messageSource.getLocalMessage(errors.getCode()));
    }
}
