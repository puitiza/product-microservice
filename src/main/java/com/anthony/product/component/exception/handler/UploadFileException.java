package com.anthony.product.component.exception.handler;

public class UploadFileException extends HandledException {

    private final String code;

    public UploadFileException(String message, String code) {
        super(message);
        this.code = code;
    }

    @Override
    public String getErrorCode() {
        return code;
    }

}
