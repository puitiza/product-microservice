package com.anthony.product.configuration.exceptionHandler;

import com.anthony.product.component.exception.BuildErrorResponse;
import com.anthony.product.component.exception.handler.ExistingElementFoundException;
import com.anthony.product.component.exception.handler.HandledException;
import com.anthony.product.component.exception.handler.NoSuchElementFoundException;
import com.anthony.product.model.exception.GlobalErrorResponse;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.anthony.product.component.exception.errors.ProductExceptionErrors.*;
import static com.anthony.product.util.Generic.GenericResponse.createErrorMessageDTO;

@Hidden
@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    private final BuildErrorResponse buildErrorResponse;
    private final MessageSourceHandler messageSourceHandler;

    @Override
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
        buildErrorResponse.addTrace(errorResponse, ex, buildErrorResponse.stackTrace(request));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NoSuchElementFoundException.class, ExistingElementFoundException.class})
    public ResponseEntity<Object> handleNoSuchElementFoundException(Exception ex, WebRequest request) {
        log.error("Failed to find the requested element", ex);
        return buildErrorResponse.structure(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(HandledException.class)
    public ResponseEntity<Object> handleAllUncaughtException(HandledException ex, WebRequest request) {
        log.error("Unknown error occurred: ", ex);
        return buildErrorResponse.structure(ex, "Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({InsufficientAuthenticationException.class})
    public ResponseEntity<Object> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex,
                                                                            HttpServletRequest request,
                                                                            WebRequest webRequest) {
        log.error("Failed to access the resource element", ex);

        var detailMessage = Optional
                .ofNullable((Exception) request.getAttribute("exception"))
                .map(Throwable::getMessage)
                .orElse("");

        GlobalErrorResponse errorResponse = new GlobalErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        errorResponse.setErrorCode(messageSourceHandler.getLocalMessage(AUTHORIZATION_ERROR.getCode()));
        errorResponse.setDetailMessage(detailMessage);

        buildErrorResponse.addTrace(errorResponse, ex, buildErrorResponse.stackTrace(webRequest));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(BadCredentialsException ex,WebRequest request) {
        log.error("Failed to autenticate the requested element", ex);

        GlobalErrorResponse errorResponse = new GlobalErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        errorResponse.setErrorCode(messageSourceHandler.getLocalMessage(AUTHORIZATION_ERROR.getCode()));
        buildErrorResponse.addTrace(errorResponse, ex, buildErrorResponse.stackTrace(request));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
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
        return new ResponseEntity<>(createErrorMessageDTO(errorResponse), HttpStatus.NOT_FOUND);
    }
}
