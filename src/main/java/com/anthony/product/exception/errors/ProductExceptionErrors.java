package com.anthony.product.exception.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductExceptionErrors {

    NO_ITEM_FOUND("item.absent.msg", "item.absent.code"),
    VALIDATION_FIELD("product.validation.field.msg", "product.validation.field.code"),
    GLOBAL_ERROR("product.global.error.msg", "product.global.error.code"),
    AUTHORIZATION_ERROR("product.authorization.error.msg", "product.authorization.error.code");

    public final String key;
    public final String code;

}
