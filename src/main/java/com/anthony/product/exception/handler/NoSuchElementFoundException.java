package com.anthony.product.exception.handler;

import com.anthony.product.exception.HandledException;
import org.springframework.http.HttpStatus;

public class NoSuchElementFoundException extends HandledException {



    public NoSuchElementFoundException(String message){
        super(message);
    }

    @Override
    public String getErrorCode() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

}
