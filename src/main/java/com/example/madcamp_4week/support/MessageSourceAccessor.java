package com.example.madcamp_4week.support;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceAccessor implements MessageSourceAware {

    private static org.springframework.context.support.MessageSourceAccessor messageSourceAccessor;

    public static String getMessage(final String code) {
        return messageSourceAccessor.getMessage(code);
    }

    public static String getMessage(final String code, final Object... args) {
        return messageSourceAccessor.getMessage(code, args);
    }

    @Override
    public void setMessageSource(final MessageSource messageSource) {
        messageSourceAccessor =
                new org.springframework.context.support.MessageSourceAccessor(
                        messageSource, Locale.getDefault());
    }
}
