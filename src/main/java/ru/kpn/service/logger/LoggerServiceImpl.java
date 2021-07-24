package ru.kpn.service.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpn.logging.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class LoggerServiceImpl implements LoggerService<CustomizableLogger.LogLevel> {

    @Autowired
    private LoggerSettings<CustomizableLogger.LogLevel> defaultLoggerSettings;

    @Autowired
    private Set<Writer> writers;

    @Autowired
    private TemplateEngine engine;

    @Autowired
    private ExtendingStrategy<Object[]> argsExtendingStrategy;

    @Autowired
    private ExtendingStrategy<String> templateExtendingStrategy;

    private final Map<Class<? extends Logger<CustomizableLogger.LogLevel>>, Logger<CustomizableLogger.LogLevel>> loggers = new HashMap<>();

    @Override
    public synchronized Logger<CustomizableLogger.LogLevel> create(Class<?> type) {
        return getLoggerOrCreate(type, defaultLoggerSettings);
    }

    @Override
    public synchronized Logger<CustomizableLogger.LogLevel> create(Class<?> type, LoggerSettings<CustomizableLogger.LogLevel> settings) {
        return getLoggerOrCreate(type, settings);
    }

    private Logger<CustomizableLogger.LogLevel> getLoggerOrCreate(Class<?> type, LoggerSettings<CustomizableLogger.LogLevel> settings) {
        return loggers.getOrDefault(type, createLogger(type, settings));
    }

    private CustomizableLogger createLogger(Class<?> type, LoggerSettings<CustomizableLogger.LogLevel> settings) {
        CustomizableLogger.CustomizableLoggerBuilder builder = CustomizableLogger.builder(type, settings);
        builder
                .engine(engine)
                .argsStrategy(argsExtendingStrategy)
                .templateStrategy(templateExtendingStrategy);
        writers.forEach(builder::writer);

        return builder.build();
    }
}
