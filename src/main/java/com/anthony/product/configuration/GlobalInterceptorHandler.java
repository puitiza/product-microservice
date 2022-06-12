package com.anthony.product.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.DispatcherType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class GlobalInterceptorHandler implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(GlobalInterceptorHandler.class);

    private final LoggingService loggingService;
/*
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       /* HandlerMethod handlerMethod = (HandlerMethod) handler;
        this.logger = LoggerFactory.getLogger(handlerMethod.getBean().getClass());
        if (logger.isInfoEnabled()) {
            logger.info("Executing Before Handler method... "
                    + "Method= " + request.getMethod() + " " +
                    "Endpoint= " + request.getRequestURL());
        }

       // var body = new CustomHttpServletRequestWrapper(request);

        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name()) && request.getMethod().equals(HttpMethod.GET.name())) {
            loggingService.logRequest(request, null);
        }
       // loggingService.logRequest(request, body);
//        loggingService.logResponse(request,response,returnBody(request));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    */

    protected String returnBody(HttpServletRequest request){
        try {
            MyRequestWrapper myRequestWrapper = new MyRequestWrapper(request);
            return myRequestWrapper.getBody();
        } catch (IOException e) {
           // log.error("get request body exception", e);
            throw new RuntimeException(e);
        }
    }

    static String extractPostRequestBody(HttpServletRequest servletRequest) {
        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper(servletRequest);
        byte[] responseArray = request.getContentAsByteArray();
        String responseBody ="";
        try {
             responseBody = new String(responseArray, request.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return responseBody;
    }
    /*
    static String extractPostRequestBody(HttpServletRequest servletRequest) {
        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper(servletRequest);
        byte[] responseArray = request.getContentAsByteArray();

        try {
            String responseBody = new String(responseArray, request.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = null;
            try {
                s = new Scanner(request.getInputStream(), StandardCharsets.UTF_8).useDelimiter("\\A");
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert s != null;
            return s.hasNext() ? s.next() : "";
        }
        return "";
    }*/

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Executing After Handler method...");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("After completing request...");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
