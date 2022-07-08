package com.anthony.product.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBooksDto {

    @JsonIgnore
    private Long authorId;

    private Long bookId;

    private String title;
}
