package com.anthony.product.service.auth;

import com.anthony.product.component.exception.handler.ExistingElementFoundException;
import com.anthony.product.model.dto.request.SignupRequest;
import com.anthony.product.repository.UserRepository;
import com.anthony.product.service.RoleService;
import com.anthony.product.service.UserDetailsServiceImpl;
import com.anthony.product.util.message_source.MessageSourceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthServiceTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private MessageSourceHandler messageSource;

    private SignupRequest signupRequest;

    @BeforeEach
    public void setup() {
        signupRequest = new SignupRequest("test", "test@email.com",
                "12345678", "ROLE_USER");
    }

    @Test
    @DisplayName("JUnit test for register user method")
    void registerUserTest() {
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(any())).thenReturn(false);

        var emp = userDetailsService.register(signupRequest);
        assertThat(emp).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for register user method which throws exception")
    void registerUserTest_thenThrowsException() {
        //when
        Mockito.when(userRepository.existsByUsername(any())).thenReturn(true);

        //then
        assertThrows(ExistingElementFoundException.class, () -> userDetailsService.register(signupRequest));
    }


}
