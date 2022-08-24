package com.anthony.product.component.log;

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
    public static final String NEW_TAB = "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
    public static final String LOG_START = "\"================================ Executing Before Handler method... ============================\"";
    public static final String LOG_END = "\"=================================== After completing request... ===============================\"";

    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        List<LogHeader> headers = buildHeadersMap(httpServletRequest);
        LogHttpRequestEntity entity = new LogHttpRequestEntity("http-request", httpServletRequest.getMethod(),
                httpServletRequest.getRequestURL(), headers, body);
        logger.debug("{} {} {}", LOG_START, NEW_TAB, new LogMessage("Request", entity));
    }

    public void registerLog(HandlerMethod handlerMethod) {
        logger.debug(" ");
        this.logger = LoggerFactory.getLogger(handlerMethod.getBean().getClass());
    }

    public void endLog() {
        logger.debug("{} {}", LOG_END, NEW_TAB);
    }

    public static String newLine() {
        return NEW_TAB;
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
