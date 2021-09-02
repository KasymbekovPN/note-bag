package ru.kpn.i18n;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class I18nImplTest {

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckSettingOfLocale.csv")
    void shouldCheckSettingOfLocale(String localeTag) {
        I18nImpl i18n = new I18nImpl(null, null);
        Locale expectedLocale = new Locale(localeTag);
        i18n.setLocale(expectedLocale);

        Locale locale = getLocale(i18n);
        assertThat(locale).isEqualTo(expectedLocale);
    }

    @Test
    void shouldCheckMessageSourceSetting() {
        I18nImpl i18n = new I18nImpl(null, null);
        MessageSource expectedMessageSource = new TestMessageSource();
        i18n.setMessageSource(expectedMessageSource);

        MessageSource messageSource = getMessageSource(i18n);
        assertThat(expectedMessageSource).isEqualTo(messageSource);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetMethod.csv")
    void shouldCheckGetMethod(String code, Integer arg, String expectedResult) {
        I18nImpl i18n = new I18nImpl(new TestMessageSource(), new Locale("en-US"));
        String result = i18n.get(code, arg);

        assertThat(expectedResult).isEqualTo(result);
    }

    @SneakyThrows
    private MessageSource getMessageSource(I18nImpl i18n) {
        Field field = i18n.getClass().getDeclaredField("messageSource");
        field.setAccessible(true);
        return (MessageSource) ReflectionUtils.getField(field, i18n);
    }

    @SneakyThrows
    private static Locale getLocale(I18nImpl i18n) {
        Field field = i18n.getClass().getDeclaredField("locale");
        field.setAccessible(true);
        return (Locale) ReflectionUtils.getField(field, i18n);
    }

    private static class TestMessageSource implements MessageSource{

        private static final String TEMPLATE_EN = "text ";

        @Override
        public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
            return null;
        }

        @Override
        public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
            if (code.equals("key")){
                return TEMPLATE_EN + String.valueOf(args[0]);
            }
            throw new NoSuchMessageException("key " + code + " doesn't exist");
        }

        @Override
        public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
            return null;
        }
    }
}
