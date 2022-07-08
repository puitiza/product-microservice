package com.anthony.product.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBookDto {

    //@NotBlank(message = "libraryId field not should be null or empty")
    private Long libraryId;

    //@NotBlank(message = "BookId field not should be null or empty")
    private Long bookId;
}
