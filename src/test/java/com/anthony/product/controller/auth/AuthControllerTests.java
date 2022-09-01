package com.anthony.product.controller.auth;

import com.anthony.product.configuration.exception_handler.ExceptionHandlerConfig;
import com.anthony.product.configuration.interceptor_log.InterceptorConfig;
import com.anthony.product.configuration.interceptor_log.request.GlobalInterceptorHandler;
import com.anthony.product.configuration.interceptor_log.response.CustomRequestBodyAdviceAdapter;
import com.anthony.product.configuration.security.JwtUtils;
import com.anthony.product.configuration.security.SecurityConfig;
import com.anthony.product.controller.AuthController;
import com.anthony.product.model.dto.request.SignupRequest;
import com.anthony.product.model.dto.response.MessageResponse;
import com.anthony.product.service.RefreshTokenService;
import com.anthony.product.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class,
        excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        ExceptionHandlerConfig.class,
                        InterceptorConfig.class,
                        GlobalInterceptorHandler.class,
                        CustomRequestBodyAdviceAdapter.class
                }))
@Import(SecurityConfig.class)
class AuthControllerTests {

    @MockBean
    UserDetailsServiceImpl userDetailsService;
    @MockBean
    RefreshTokenService refreshTokenService;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    JwtUtils jwtUtils;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("POST /api/auth/sign-up success")
    void testRegisterUser() throws Exception {

        var record = new SignupRequest("dummy", "dummy@email.com", "123456", "ROLE_USER");
        var response = new MessageResponse("success");

        Mockito.when(userDetailsService.register(record)).thenReturn(response);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/auth/sign-up")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andDo(print());

    }

    /*
    private static UserDetails dummy;
    private static String jwtToken;

    @BeforeEach
    public void setUp() {
        dummy = new User("user@email.com", "12345678", new ArrayList<>());
        //jwtToken = jwtUtil.generateToken(dummy);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithAnonymousUser
    void testLoginReturnsJwt() throws Exception {

        LoginRequest loginRequest = new LoginRequest("user@email.com", "12345678");
        //AuthenticationResponse authenticationResponse = new AuthenticationResponse("anyjwt");

        String jsonRequest = objectMapper.writeValueAsString(loginRequest);
        // String jsonResponse = asJsonString(authenticationResponse);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/auth/sign-in")
                //.with(csrf())
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);
        when(authentication.isAuthenticated()).thenReturn(true);

        when(authenticationManager.authenticate(any())).thenReturn(authentication); // Failing here

        when(jwtUtils.generateJwtToken(authentication)).thenReturn("124");
        when(userDetailsService.loadUserByUsername(eq("user@email.com"))).thenReturn(dummy);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                //.andExpect(content().json(jsonResponse, true))
                //.andExpect(jsonPath("$.jwt").value(isNotNull()))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }
     */
}
