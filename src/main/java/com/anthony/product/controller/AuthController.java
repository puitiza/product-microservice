package com.anthony.product.controller;

import com.anthony.product.model.dto.UserDetailsImpl;
import com.anthony.product.model.dto.request.LoginRequest;
import com.anthony.product.model.dto.request.SignupRequest;
import com.anthony.product.model.dto.response.JwtResponse;
import com.anthony.product.model.dto.response.MessageResponse;
import com.anthony.product.service.UserDetailsServiceImpl;
import com.anthony.product.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public record AuthController(UserDetailsServiceImpl userDetailsService,
                             AuthenticationManager authenticationManager, JwtUtils jwtUtils) {

    @PostMapping("/sign-in")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetailsService.createToken(userDetails, jwt);
    }

    @PostMapping("/signup")
    public MessageResponse registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return userDetailsService.register(signUpRequest);
    }
}
