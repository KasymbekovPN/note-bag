package ru.kpn.config.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpn.logging.*;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class LoggerSettingsConfig {

    @Bean
    public LoggerSettings<CustomizableLogger.LogLevel> defaultLoggerSettings(){
        return CustomizableLoggerSettings.builder()
                .enable(CustomizableLogger.LogLevel.TRACE)
                .enable(CustomizableLogger.LogLevel.DEBUG)
                .enable(CustomizableLogger.LogLevel.INFO)
                .enable(CustomizableLogger.LogLevel.WARN)
                .enable(CustomizableLogger.LogLevel.ERROR)
                .build();
    }

    @Bean
    public Set<Writer> writers(){
        return new HashSet<>(){{
            add(new SoutWriter());
        }};
    }

    @Bean
    public TemplateEngine engine(){
        return new LoggerTemplateEngine();
    }

    @Bean
    public ExtendingStrategy<Object[]> argsExtendingStrategy(){
        return new ArgsExtendingStrategy();
    }

    @Bean
    public ExtendingStrategy<String> templateExtendingStrategy(){
        return new TemplateExtendingStrategy();
    }
}

