package com.anthony.product.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @NotBlank(message = "name field not should be null or empty")
    @Size(min = 5, max = 64, message = "location field should be minimum size 5 and maximum 64")
    private String location;

}
