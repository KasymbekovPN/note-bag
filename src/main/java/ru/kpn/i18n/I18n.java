package ru.kpn.i18n;

import org.springframework.context.MessageSource;

import java.util.Locale;

public interface I18n {
    default void setLocale(Locale locale){}
    default void setMessageSource(MessageSource messageSource){}
    default String get(String code, Object... args){return "";}
}
