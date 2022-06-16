package com.anthony.product.interceptor.Log;

import com.anthony.product.model.log.LogHttpRequestEntity;
import com.anthony.product.model.log.LogHttpRequestEntity.LogHeader;
import com.anthony.product.model.log.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Component
public class LoggingService {
    private Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        List<LogHeader> headers = buildHeadersMap(httpServletRequest);
        LogHttpRequestEntity entity = new LogHttpRequestEntity("http-request", httpServletRequest.getMethod(),
                httpServletRequest.getRequestURL(), headers, body);
        logger.debug("\"================================ Executing Before Handler method... ============================\""
                + newLine() +
                new LogMessage("Request", entity));
    }

    public void registerLog(HandlerMethod handlerMethod) {
        System.out.println();
        this.logger = LoggerFactory.getLogger(handlerMethod.getBean().getClass());
    }

    public void endLog() {
        logger.debug("\"=================================== After completing request... ===============================\""
                + newLine()
        );
    }

    protected String newLine() {
        return "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ";
    }

    protected List<LogHeader> buildHeadersMap(HttpServletRequest request) {
        List<LogHeader> list = new ArrayList<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            list.add(new LogHeader(key, value));
        }
        return list;
    }
}
