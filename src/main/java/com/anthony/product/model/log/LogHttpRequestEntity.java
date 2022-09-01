package com.anthony.product.model.log;

import java.util.List;

public record LogHttpRequestEntity(String type, String method, StringBuffer path, List<LogHeader> headers,
                                   Object body) {

    public static String NEW_TAB = "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";

    @Override
    public String toString() {
        return "LogHttpRequestEntity{" +
                "type='" + type + '\'' + "," + NEW_TAB +
                " method='" + method + '\'' + "," + NEW_TAB +
                " path='" + path + '\'' + "," + NEW_TAB +
                " headers=" + headers + "," + NEW_TAB +
                " body=" + body + "," + NEW_TAB +
                '}';
    }

    public record LogHeader(String key, String value) {

    }
}
