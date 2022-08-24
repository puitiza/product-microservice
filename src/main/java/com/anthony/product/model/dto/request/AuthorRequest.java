package com.anthony.product.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class AuthorRequest {

    @NotBlank(message = "name field not should be null or empty")
    @Size(min = 2, max = 64, message = "name field should be minimum size 2 and maximum 64")
    @Schema(name = "name", description = "Name of Author", required = true, example = "Abram")
    private String name;

}
