package ru.kpn.logging;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

class CustomizableLogger implements Logger<CustomizableLogger.LogLevel> {

    private final Class<?> type;
    private final Set<Writer> writers;
    private final TemplateEngine engine;
    private final LoggerExtender<LogLevel> extender;

    private LoggerSetting<LogLevel> setting;

    private CustomizableLogger(Class<?> type,
                               Set<Writer> writers,
                               TemplateEngine engine,
                               LoggerExtender<LogLevel> extender,
                               LoggerSetting<LogLevel> setting) {
        this.type = type;
        this.writers = writers;
        this.engine = engine;
        this.extender = extender;
        this.setting = setting;
    }

    public static CustomizableLoggerBuilder builder(Class<?> type, LoggerSetting<LogLevel> setting) {
        return new CustomizableLoggerBuilder(type, setting);
    }

    @Override
    public LoggerSetting<LogLevel> getSetting() {
        return setting;
    }

    @Override
    public void setSetting(LoggerSetting<LogLevel> setting) {
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
        private final LoggerSetting<LogLevel> setting;
        private TemplateEngine engine;
        private LoggerExtender<LogLevel> extender;

        CustomizableLoggerBuilder(Class<?> type, LoggerSetting<LogLevel> setting) {
            this.type = type;
            this.setting = setting;
        }

        public CustomizableLogger build() {
            return new CustomizableLogger(
                    type,
                    writers,
                    engine,
                    extender,
                    setting
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

        public CustomizableLoggerBuilder extender(LoggerExtender<LogLevel> extender){
            this.extender = extender;
            return this;
        }
    }
}
