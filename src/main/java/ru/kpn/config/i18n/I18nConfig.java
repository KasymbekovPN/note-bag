package ru.kpn.config.i18n;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.kpn.i18n.I18n;
import ru.kpn.i18n.I18nImpl;

import java.util.Locale;

@Configuration
@Setter
@ConfigurationProperties(prefix = "i18n")
public class I18nConfig {

    private String locale;
    private String encoding;
    private String resource;

    @Bean
    public I18n i18n(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(resource);
        messageSource.setDefaultEncoding(encoding);

        return new I18nImpl(messageSource, new Locale(locale));
    }
}
