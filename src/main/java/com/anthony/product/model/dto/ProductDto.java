package com.anthony.product.model.dto;

import com.anthony.product.model.Enum.Category;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example= "xiaomi", description = "Name of product")
    private String name;

    @NotNull(message = "price field not should be null or empty")
    @Schema(example= "10", description = "Price of product")
    private Double price;

    @Schema(example= "21.50", description = "Weight of product")
    private Double weight;

    @Schema(example= "MOBILE", description = "Category of product")
    private Category category;
}
