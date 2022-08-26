package com.anthony.product.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class TokenRefreshRequest {

    @NotBlank(message = "'refreshToken' field not should be null or empty")
    private String refreshToken;

}
