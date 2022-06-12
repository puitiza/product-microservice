package com.anthony.product.configuration;

import com.anthony.product.controller.ProductController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class GlobalInterceptorHandler implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(GlobalInterceptorHandler.class);
    private final LoggingService loggingService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name()) && request.getMethod().equals(HttpMethod.GET.name())) {
          //  logger.logRequest(request, null);
       // }

        if (handler instanceof HandlerMethod &&
                ((HandlerMethod)handler).getBean() instanceof ProductController)
        {
            logger.info("Executing Before Handler method...");
            if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name()) && request.getMethod().equals(HttpMethod.GET.name())) {
                loggingService.logRequest(request, null);
            }
            return true;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       // logger.info("Executing After Handler method...");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod &&
                ((HandlerMethod)handler).getBean() instanceof ProductController)
        {
            logger.info("After completing request...");
        }

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
