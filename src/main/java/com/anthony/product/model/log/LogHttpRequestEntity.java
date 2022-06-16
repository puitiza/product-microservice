package com.anthony.product.model.log;

import java.util.List;

public record LogHttpRequestEntity(String type, String method, StringBuffer path, List<LogHeader> headers,
                                   Object body) {

    @Override
    public String toString() {
        return "LogHttpRequestEntity{" +
                "type='" + type + '\'' + "," +
                "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                " method='" + method + '\'' + "," +
                "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                " path='" + path + '\'' + "," +
                "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                " headers=" + headers + "," +
                "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                " body=" + body + "," +
                "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                '}';
    }

    public record LogHeader(String key, String value) {

    }
}
