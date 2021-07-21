package ru.kpn.config.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.CustomizableLoggerSettings;
import ru.kpn.logging.LoggerSettings;

@Configuration
public class DefaultLoggerSettingsConfig {

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
}

