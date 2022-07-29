package com.anthony.product.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "'username' field not should be null or empty")
    private String username;

    @NotBlank(message = "'password' field not should be null or empty")
    private String password;
}
