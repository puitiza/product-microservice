package com.anthony.product.model.dto;

import com.anthony.product.model.Enum.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class ProductDto {

    @NotBlank(message = "name field not should be null or empty")
    @Size(min = 5, max = 64, message = "name field should be minimum size 5 and maximum 64")
    private String name;

    @NotNull(message = "price field not should be null or empty")
    private Double price;

    private Double weight;

    private Category category;
}
