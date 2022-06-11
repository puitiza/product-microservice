package com.anthony.product.exception.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductExceptionErrors {

    NO_ITEM_FOUND("item.absent.msg","item.absent.code");

    public final String key;
    public final String errorCode;

}
