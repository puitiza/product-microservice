package com.anthony.product.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class ImageRequest {

    @NotBlank(message = "email field not should be null or empty")
    @Size(min = 8, max = 64, message = "email field should be minimum size 8 and maximum 64")
    @Schema(name = "email", description = "Email of Image", required = true, example = "test@gmail.com")
    private String email;

    @Transient
    private MultipartFile profilePicImageFile;
}
