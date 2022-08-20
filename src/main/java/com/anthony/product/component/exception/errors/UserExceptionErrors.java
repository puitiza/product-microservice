package com.anthony.product.component.exception.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserExceptionErrors {

    USERNAME_FOUND("user.exist.by.username.msg", "user.exist.by.username.code"),
    EMAIL_FOUND("user.exist.by.email.error.msg", "user.exist.by.username.code"),
    ROLE_NOT_FOUND("user.role.error.msg", "user.exist.by.username.code");

    public final String key;
    public final String code;

}
