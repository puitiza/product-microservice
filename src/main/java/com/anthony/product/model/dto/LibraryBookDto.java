package com.anthony.product.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBookDto {

    @JsonIgnore
    private Long libraryId;

    @NotNull(message = "bookId should no be null")
    private Long bookId;
}
