package com.anthony.product.util.MessageSource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public record MessageSourceHandler(MessageSource messageSource) {

    public String getLocalMessage(String key, Optional<String> optionalLocale, String... params) {
        var locale = optionalLocale.map(Locale::forLanguageTag).orElse(Locale.US);
        return messageSource.getMessage(key,
                params,
                locale);
    }

    public String getLocalMessage(String code) {
        return messageSource.getMessage(code, null, Locale.US);

    }
}
