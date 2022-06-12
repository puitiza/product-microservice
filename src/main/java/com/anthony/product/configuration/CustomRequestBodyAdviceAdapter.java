package com.anthony.product.configuration;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018 <br/>
 *        This AdviceAdapter to log for Post Http Request.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

    private final LoggingService loggingService;
    private final HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        ObjectMapper mapper = new ObjectMapper();
        var data= mapper.writeValueAsString(body);
        loggingService.logRequest(httpServletRequest, data);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
