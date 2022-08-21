package com.anthony.product.component.exception.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductExceptionErrors {

    NO_ITEM_FOUND("item.absent.msg", "item.absent.code"),
    VALIDATION_FIELD("product.validation.field.msg", "product.validation.field.code"),
    GLOBAL_ERROR("product.global.error.msg", "product.global.error.code"),
    AUTHORIZATION_ERROR("product.authorization.error.msg", "product.authorization.error.code"),
    DENIED_ACCESS_ERROR("product.denied.access.error.msg","product.denied.access.error.code"),
    REFRESH_TOKEN_ERROR("product.refresh.token.error.msg","product.denied.access.error.code"),
    REFRESH_TOKEN_EXPIRED_ERROR("product.refresh.token.expired.error.msg","product.denied.access.error.code"),
    STORE_FILE_ERROR("product.store.file.error.msg","product.store.file.error.code");

    public final String key;
    public final String code;

}
