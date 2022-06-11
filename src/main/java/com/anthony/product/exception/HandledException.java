package com.anthony.product.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public abstract class HandledException extends RuntimeException {

    protected HandledException() {
        super();
    }

    protected HandledException(Throwable e) {
        super(e);
    }

    protected HandledException(String e) {
        super(e);
    }

    public abstract String getErrorCode();

    public abstract String getErrorMessage();

    public ZonedDateTime getZoneDateTime() {
        return ZonedDateTime.now();
    }

    public String getErrorDetail() {
        try {
            return this.getCause().toString();
        } catch (Exception e) {
            return "There is no error detail";
        }
    }

}
