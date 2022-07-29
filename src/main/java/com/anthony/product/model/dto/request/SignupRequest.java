package com.anthony.product.model.dto.request;

import com.anthony.product.util.CustomValidation.Alphanumeric;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
public class SignupRequest {

    //@Alphanumeric(message = "Only alphanumeric characters except these special characters('@','.','_','-') are allowed")
    @NotBlank(message = "'username' field not should be null or empty")
    @Size(min = 3, max = 20, message = "'username' field should be minimum size 3 and maximum 20")
    private String username;

    @NotBlank(message = "'email' field not should be null or empty")
    @Size(min = 5, max = 50, message = "'email' field should be minimum size 5 and maximum 50")
    @Email(message = "email address must be valid")
    private String email;

    @NotBlank(message = "'password' field not should be null or empty")
    @Size(min = 6, max = 40, message = "'password' field should be minimum size 6 and maximum 40")
    private String password;

    private Set<String> role;
}
