package com.anthony.product.exception;

import com.anthony.product.model.exception.GlobalErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@Component
public class BuildErrorResponse implements BuildStructureBody {
    public static final String TRACE = "trace";
    @Value("${configuration.trace:false}")
    private boolean printStackTrace;

    protected boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }

    @Override
    public ResponseEntity<Object> structure(Exception exception, String message, HttpStatus httpStatus, WebRequest request) {
        GlobalErrorResponse errorResponse = new GlobalErrorResponse(httpStatus.value(), message);
        if (printStackTrace && isTraceOn(request)) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @Override
    public ResponseEntity<Object> structure(Exception exception, HttpStatus httpStatus, WebRequest request) {
        return structure(exception, exception.getMessage(), httpStatus, request);
    }
}
