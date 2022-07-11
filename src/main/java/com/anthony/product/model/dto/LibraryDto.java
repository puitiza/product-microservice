package com.anthony.product.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDto {

    @NotBlank(message = "name field not should be null or empty")
    private String name;

    @NotBlank(message = "addressId field not should be null or empty")
    private String addressId;
}
