package ru.kpn.logging;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomizableLogger implements Logger<CustomizableLogger.LogLevel> {

    private final Class<?> type;
    private final Set<Writer> writers;
    private final TemplateEngine engine;
    private final ExtendingStrategy<Object[]> argsExtendingStrategy;
    private final ExtendingStrategy<String> templateExtendingStrategy;

    private LoggerSettings<LogLevel> setting;

    private CustomizableLogger(Class<?> type,
                              Set<Writer> writers,
                              TemplateEngine engine,
                              LoggerSettings<LogLevel> setting,
                              ExtendingStrategy<Object[]> argsExtendingStrategy,
                              ExtendingStrategy<String> templateExtendingStrategy) {
        this.type = type;
        this.writers = writers;
        this.engine = engine;
        this.setting = setting;
        this.argsExtendingStrategy = argsExtendingStrategy;
        this.templateExtendingStrategy = templateExtendingStrategy;
    }

    public static CustomizableLoggerBuilder builder(Class<?> type, LoggerSettings<LogLevel> setting) {
        return new CustomizableLoggerBuilder(type, setting);
    }

    @Override
    public LoggerSettings<LogLevel> getSetting() {
        return setting;
    }

    @Override
    public void setSetting(LoggerSettings<LogLevel> setting) {
        this.setting = setting;
    }

    @Override
    public void trace(String template, Object... args) {
        log(LogLevel.TRACE, template, args);
    }

    @Override
    public void debug(String template, Object... args) {
        log(LogLevel.DEBUG, template, args);
    }

    @Override
    public void info(String template, Object... args) {
        log(LogLevel.INFO, template, args);
    }

    @Override
    public void warn(String template, Object... args) {
        log(LogLevel.WARN, template, args);
    }

    @Override
    public void error(String template, Object... args) {
        log(LogLevel.ERROR, template, args);
    }

    private synchronized void log(LogLevel logLevel, String template, Object... args) {
        if (setting.get(logLevel)){
            String log = calculateLogContent(logLevel, template, args);
            for (Writer writer : writers) {
                try {
                    writer.write(log);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String calculateLogContent(LogLevel logLevel, String template, Object[] args) {
        return engine.fill(
                templateExtendingStrategy.execute(template),
                argsExtendingStrategy.execute(args, logLevel, type)
        );
    }

    public enum LogLevel{
        UNKNOWN,
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    public static class CustomizableLoggerBuilder {

        private final Class<?> type;
        private final Set<Writer> writers = new HashSet<>();
        private final LoggerSettings<LogLevel> setting;
        private TemplateEngine engine;
        private ExtendingStrategy<Object[]> argsExtendingStrategy;
        private ExtendingStrategy<String> templateExtendingStrategy;

        CustomizableLoggerBuilder(Class<?> type, LoggerSettings<LogLevel> setting) {
            this.type = type;
            this.setting = setting;
        }

        public CustomizableLogger build() {
            return new CustomizableLogger(
                    type,
                    writers,
                    engine,
                    setting,
                    argsExtendingStrategy,
                    templateExtendingStrategy
            );
        }

        public CustomizableLoggerBuilder writer(Writer writer) {
            writers.add(writer);
            return this;
        }

        public CustomizableLoggerBuilder engine(TemplateEngine engine) {
            this.engine = engine;
            return this;
        }

        public CustomizableLoggerBuilder argsStrategy(ExtendingStrategy<Object[]> argsExtendingStrategy) {
            this.argsExtendingStrategy = argsExtendingStrategy;
            return this;
        }

        public CustomizableLoggerBuilder templateStrategy(ExtendingStrategy<String> templateExtendingStrategy) {
            this.templateExtendingStrategy = templateExtendingStrategy;
            return this;
        }
    }
}
