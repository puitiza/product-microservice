package com.anthony.product.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenRefreshRequest {

    @NotBlank(message = "'refreshToken' field not should be null or empty")
    private String refreshToken;

}
