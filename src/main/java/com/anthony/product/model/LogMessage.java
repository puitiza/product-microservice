package com.anthony.product.model;

public record LogMessage(String process, Object payload) {
    @Override
    public String toString() {
        return "LogMessage{" +
                "process='" + process + '\'' + "," +
                "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                "payload=" + payload +
                "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" +
                '}';
    }
}
