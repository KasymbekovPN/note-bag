package ru.kpn.logging;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

class CustomizableLogger implements Logger<CustomizableLogger.LogLevel> {
    private final Class<?> type;
    private final EnumMap<LogLevel, Boolean> levelStates;
    private final Set<Writer> writers;
    private final TemplateEngine engine;
    private final LoggerExtender<LogLevel> extender;

    private CustomizableLogger(Class<?> type,
                               EnumMap<LogLevel, Boolean> levelStates,
                               Set<Writer> writers,
                               TemplateEngine engine,
                               LoggerExtender<LogLevel> extender) {
        this.type = type;
        this.levelStates = levelStates;
        this.writers = writers;
        this.engine = engine;
        this.extender = extender;
    }

    public static CustomizableLoggerBuilder builder(Class<?> type) {
        return new CustomizableLoggerBuilder(type);
    }

    @Override
    public synchronized Boolean isEnabled(LogLevel logLevel) {
        return levelStates.get(logLevel);
    }

    @Override
    public synchronized void enable(LogLevel logLevel) {
        levelStates.put(logLevel, true);
    }

    @Override
    public synchronized void disable(LogLevel logLevel) {
        levelStates.put(logLevel, false);
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
        if (checkLogLevel(logLevel)){
            String log = engine.fill(extender.extendTemplate(template), extender.extendArgs(args, logLevel, type));
            for (Writer writer : writers) {
                try {
                    writer.write(log);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkLogLevel(LogLevel logLevel) {
        return levelStates.get(logLevel);
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

        private final EnumMap<LogLevel, Boolean> levelStates = new EnumMap<>(LogLevel.class);
        private final Class<?> type;
        private final Set<Writer> writers = new HashSet<>();
        private TemplateEngine engine;
        private LoggerExtender<LogLevel> extender;

        CustomizableLoggerBuilder(Class<?> type) {
            this.type = type;
        }

        public CustomizableLogger build() {
            fillLevelStates();
            return new CustomizableLogger(
                    type,
                    levelStates,
                    writers,
                    engine,
                    extender
            );
        }

        private void fillLevelStates() {
            for (LogLevel logLevel : LogLevel.values()) {
                if (!levelStates.containsKey(logLevel)){
                    levelStates.put(logLevel, false);
                }
            }
        }

        public CustomizableLoggerBuilder enable(LogLevel logLevel) {
            levelStates.put(logLevel, true);
            return this;
        }

        public CustomizableLoggerBuilder writer(Writer writer) {
            writers.add(writer);
            return this;
        }

        public CustomizableLoggerBuilder engine(TemplateEngine engine) {
            this.engine = engine;
            return this;
        }

        public CustomizableLoggerBuilder extender(LoggerExtender<LogLevel> extender){
            this.extender = extender;
            return this;
        }
    }
}
