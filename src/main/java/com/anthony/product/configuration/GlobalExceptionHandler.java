package com.anthony.product.configuration;

import com.anthony.product.exception.BuildErrorResponse;
import com.anthony.product.exception.handler.HandledException;
import com.anthony.product.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.exception.GlobalErrorResponse;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.anthony.product.exception.errors.ProductExceptionErrors.GLOBAL_ERROR;
import static com.anthony.product.exception.errors.ProductExceptionErrors.VALIDATION_FIELD;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final BuildErrorResponse buildErrorResponse;
    private final MessageSourceHandler messageSourceHandler;

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.error("Failed to validate the requested element", ex);
        GlobalErrorResponse errorResponse = new GlobalErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. Check 'errors' field for details.");
        errorResponse.setErrorCode(messageSourceHandler.getLocalMessage(VALIDATION_FIELD.getCode()));
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        buildErrorResponse.addTrace(errorResponse, ex, false);
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementFoundException itemNotFoundException, WebRequest request) {
        log.error("Failed to find the requested element", itemNotFoundException);
        return buildErrorResponse.structure(itemNotFoundException, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(HandledException exception, WebRequest request) {
        log.error("Unknown error occurred: ", exception);
        return buildErrorResponse.structure(exception, "Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Exception Internal error occurred: ", ex);
        return buildErrorResponse.structure(ex, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        GlobalErrorResponse errorResponse = new GlobalErrorResponse(status.value(), ex.getMessage());
        errorResponse.setErrorCode(messageSourceHandler.getLocalMessage(GLOBAL_ERROR.getCode()));
        buildErrorResponse.addTrace(errorResponse, ex, false);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
