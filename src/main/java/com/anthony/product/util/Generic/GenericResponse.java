package com.anthony.product.util.Generic;

import com.anthony.product.model.entity.ProductEntity;
import com.anthony.product.model.exception.GlobalErrorResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> implements Serializable {
    /**
     * Contains the specific response data object.
     */
    @Schema(anyOf = {ProductEntity.class})
    private T data;
    /**
     * Indicate the operation was a success true or have errors false
     */
    private boolean success;
    /**
     * Exception detail if exist.
     */
    private GlobalErrorResponse exception;

    public static Object createErrorMessageDTO(GlobalErrorResponse errorMessage) {
        return GenericResponse.builder()
                .success(false)
                .exception(errorMessage).build();
    }
}
