package ru.kpn.config.i18n;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.kpn.i18n.I18n;
import ru.kpn.i18n.I18nImpl;
import ru.kpn.i18n.adapter.arguments.ArgumentsAdapter;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.i18n.builder.MessageBuilderFactoryImpl;

import java.util.List;
import java.util.Locale;

@Configuration
@Setter
@ConfigurationProperties(prefix = "i18n")
public class MessageBuilderFactoryImplConfig {

    private String locale;
    private String encoding;
    private String resource;

    @Bean
    public MessageBuilderFactory messageBuilderFactory(List<ArgumentsAdapter> argumentsAdapters){
        MessageBuilderFactoryImpl.Builder builder = MessageBuilderFactoryImpl.builder().i18n(createI18n());
        for (ArgumentsAdapter argumentsAdapter : argumentsAdapters) {
            builder.adapter(argumentsAdapter);
        }

        return builder.build();
    }

    private I18n createI18n() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(resource);
        messageSource.setDefaultEncoding(encoding);

        return new I18nImpl(messageSource, new Locale(locale));
    }
}
