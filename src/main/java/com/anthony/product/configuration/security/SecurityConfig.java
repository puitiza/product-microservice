package com.anthony.product.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Spring Security provides some annotations for pre- and post-invocation authorization checks,
 * filtering of submitted collection arguments or return values: @PreAuthorize, @PreFilter, @PostAuthorize and @PostFilter.
 * To enable Method Security Expressions, we use @EnableGlobalMethodSecurity annotation:
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * .headers(...) -> This tells the browser that the page can only be displayed in a frame on the same origin as the page itself.
     * However, if a different (potentially malicious) website tried to embed one the pages, the browser would not allow it.
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception When something wrong happens
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
//              Instead of disabling it, it is sufficient to set X-Frame-Options to SAME ORIGIN, for this use case.
                .and()
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(unauthorizedEntryPoint())
//              make sure we use stateless session; session won't be used to store user's state.
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//              this is another and better way to handle swagger documentation
                .and()
                .authorizeRequests()
                    .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                            "/favicon.ico", "/h2-console/**").permitAll()
                .and()
                .authorizeRequests()
                    .antMatchers("/api/auth/**").permitAll()
                    .antMatchers("/api/test/**").permitAll()
//                  .antMatchers("/**").permitAll() // permit all the routers after swagger-ui.html, but it's not recommend
                .and()
                .authorizeRequests()
                    .antMatchers("/image/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/product").permitAll()
                    .antMatchers("/product/{id}").hasRole("ADMIN")
                    .antMatchers("/product/by-name/{name}").hasAnyRole("USER", "MODERATOR")
                    .anyRequest().authenticated();

// 		Add a filter to validate the tokens with every request
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) ->
                handlerExceptionResolver.resolveException(request, response, null, authException);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) ->
                handlerExceptionResolver.resolveException(request, response, null, ex);
    }

}
