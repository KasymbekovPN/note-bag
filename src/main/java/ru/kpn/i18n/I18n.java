package ru.kpn.i18n;

import org.springframework.context.MessageSource;

import java.util.Locale;

public interface I18n {
    void setLocale(Locale locale);
    void setMessageSource(MessageSource messageSource);
    String get(String code, Object... args);
}
