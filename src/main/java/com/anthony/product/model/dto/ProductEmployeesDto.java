package com.anthony.product.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEmployeesDto {

    @JsonIgnore
    private Long productId;

    private Long employeeId;

}
