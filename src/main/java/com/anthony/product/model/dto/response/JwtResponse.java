package com.anthony.product.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class JwtResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String token;
    private final String type = "Bearer";
}
