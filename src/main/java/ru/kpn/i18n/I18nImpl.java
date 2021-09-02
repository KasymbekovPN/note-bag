package ru.kpn.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class I18nImpl implements I18n {

    private MessageSource messageSource;
    private Locale locale;

    public I18nImpl(MessageSource messageSource,
                    Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String get(String code, Object... args) {
        try{
            return messageSource.getMessage(code, args, locale);
        }
        catch (NoSuchMessageException ex){
            return code;
        }
    }
}
