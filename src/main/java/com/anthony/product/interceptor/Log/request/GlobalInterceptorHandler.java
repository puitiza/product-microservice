package com.anthony.product.interceptor.Log.request;

import com.anthony.product.controller.ProductController;
import com.anthony.product.interceptor.Log.LoggingService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public record GlobalInterceptorHandler(LoggingService loggingService) implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof  HandlerMethod item){
            // Here add Controller's name if you want to show logger
            if (item.getBean() instanceof ProductController) {
                loggingService.registerLog(item);
                if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name()) && request.getMethod().equals(HttpMethod.GET.name())) {
                    loggingService.logRequest(request, null);
                }
                return true;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod &&
                ((HandlerMethod) handler).getBean() instanceof ProductController) {
            loggingService.endLog();
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
