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
    DENIED_ACCESS_ERROR("product.denied.access.error.msg", Constants.DENIED_CODE),
    REFRESH_TOKEN_ERROR("product.refresh.token.error.msg", Constants.DENIED_CODE),
    REFRESH_TOKEN_EXPIRED_ERROR("product.refresh.token.expired.error.msg", Constants.DENIED_CODE),
    STORE_FILE_ERROR("product.store.file.error.msg", Constants.FILE_CODE),
    UPLOAD_FILE_ERROR("product.store.upload.file.error.msg", Constants.FILE_CODE);

    public final String key;
    public final String code;

    private static class Constants {
        public static final String DENIED_CODE = "product.denied.access.error.code";
        public static final String FILE_CODE = "product.store.file.error.code";
    }

}
