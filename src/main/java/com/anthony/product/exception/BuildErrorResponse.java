package com.anthony.product.exception;

import com.anthony.product.exception.handler.HandledException;
import com.anthony.product.model.exception.GlobalErrorResponse;
import com.anthony.product.util.MessageSource.MessageSourceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.anthony.product.exception.errors.ProductExceptionErrors.GLOBAL_ERROR;
import static com.anthony.product.util.Generic.GenericResponse.createErrorMessageDTO;

@Component
@RequiredArgsConstructor
public class BuildErrorResponse implements BuildStructureBody {
    public static final String TRACE = "trace";
    @Value("${configuration.trace:false}")
    private boolean printStackTrace;

    private final MessageSourceHandler messageSource;

    protected boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }

    public void addTrace(GlobalErrorResponse errorResponse, Exception exception, boolean trace) {
        if (trace) {
            var depth = (Arrays.stream(exception.getStackTrace()).count() / 8);
            var stackTrace = Arrays.stream(exception.getStackTrace()).limit(depth)
                    .map(String::valueOf)
                    .map(s -> "   at " + s)
                    .collect(Collectors.joining(System.lineSeparator()));
            String message = exception + System.lineSeparator() + stackTrace;
            errorResponse.setDebugMessage(message);
        }

        var stackTrace2 = Arrays.stream(exception.getStackTrace()).limit(4)
                .map(String::valueOf)
                .collect(Collectors.toCollection(ArrayList::new));
        errorResponse.setStackTrace(stackTrace2);

        var timestamp = ZonedDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss z");
        errorResponse.setTimestamp(timestamp.format(formatter2));

        if (exception instanceof HandledException handledException) {
            errorResponse.setErrorCode(
                    Optional.ofNullable(handledException.getErrorCode())
                            .orElse(messageSource.getLocalMessage(GLOBAL_ERROR.getCode()))
            );
        }
    }

    @Override
    public ResponseEntity<Object> structure(Exception exception, String message, HttpStatus httpStatus, WebRequest request) {
        GlobalErrorResponse errorResponse = new GlobalErrorResponse(httpStatus.value(), message);
        addTrace(errorResponse, exception, (printStackTrace && isTraceOn(request)));
        return ResponseEntity.status(httpStatus).body(createErrorMessageDTO(errorResponse));
    }

    @Override
    public ResponseEntity<Object> structure(Exception exception, HttpStatus httpStatus, WebRequest request) {
        return structure(exception, exception.getMessage(), httpStatus, request);
    }
}
