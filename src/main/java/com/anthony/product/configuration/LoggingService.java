package com.anthony.product.configuration;

import com.anthony.product.model.LogHeader;
import com.anthony.product.model.LogHttpRequestEntity;
import com.anthony.product.model.LogMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@Component
public class LoggingService {
    private static final Logger logger = LogManager.getLogger(LoggingService.class);

    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        List<LogHeader> headers = buildHeadersMap(httpServletRequest);
        LogHttpRequestEntity entity = new LogHttpRequestEntity("http-request",httpServletRequest.getMethod(),
                httpServletRequest.getRequestURL(),headers,body);
        logger.info("\"============================== Executing Before Handler method... ==========================\"" //+ System.lineSeparator()+
                +newLine()+
                new LogMessage("Request", entity));
    }

    protected String newLine(){
        return "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ";
    }

    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) throws IOException {
        List<LogHeader> headers = buildHeadersMap(httpServletResponse);
        LogHttpRequestEntity entity = new LogHttpRequestEntity("http-response",httpServletRequest.getMethod(),
                httpServletRequest.getRequestURL(),headers,body);
        logger.info(new LogMessage("Response", entity));
    }



    private List<LogHeader> buildHeadersMap(HttpServletRequest request) {
        List<LogHeader> list = new ArrayList<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            list.add(new LogHeader(key, value));
        }
        return list;
    }

    private List<LogHeader> buildHeadersMap(HttpServletResponse response) {
        List<LogHeader> list = new ArrayList<>();
        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            list.add(new LogHeader(header, response.getHeader(header)));
        }
        return list;
    }
}
