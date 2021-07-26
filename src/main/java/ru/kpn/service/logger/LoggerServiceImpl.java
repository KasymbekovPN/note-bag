package ru.kpn.service.logger;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpn.logging.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class LoggerServiceImpl implements LoggerService<CustomizableLogger.LogLevel> {

    private final LoggerSettings<CustomizableLogger.LogLevel> defaultLoggerSettings;
    private final Set<Writer> writers;
    private final TemplateEngine engine;
    private final ExtendingStrategy<Object[]> argsExtendingStrategy;
    private final ExtendingStrategy<String> templateExtendingStrategy;

    private final Map<Class<?>, Logger<CustomizableLogger.LogLevel>> loggers = new HashMap<>();

    @Override
    public synchronized Logger<CustomizableLogger.LogLevel> create(Class<?> type) {
        return getLoggerOrCreate(type, defaultLoggerSettings);
    }

    @Override
    public synchronized Logger<CustomizableLogger.LogLevel> create(Class<?> type, LoggerSettings<CustomizableLogger.LogLevel> settings) {
        return getLoggerOrCreate(type, settings);
    }

    private Logger<CustomizableLogger.LogLevel> getLoggerOrCreate(Class<?> type, LoggerSettings<CustomizableLogger.LogLevel> settings) {
        if (!loggers.containsKey(type)){
            loggers.put(type, createLogger(type, settings));
        }
        return loggers.get(type);
    }

    private Logger<CustomizableLogger.LogLevel> createLogger(Class<?> type, LoggerSettings<CustomizableLogger.LogLevel> settings) {
        CustomizableLogger.CustomizableLoggerBuilder builder = CustomizableLogger.builder(type, settings);
        builder
                .engine(engine)
                .argsStrategy(argsExtendingStrategy)
                .templateStrategy(templateExtendingStrategy);
        writers.forEach(builder::writer);

        return builder.build();
    }
}
