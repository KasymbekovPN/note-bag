package ru.kpn.logging;

import java.util.EnumMap;

class CustomizableLogger implements Logger<CustomizableLogger.LogLevel> {
    private final Class<?> type;
    private final EnumMap<LogLevel, Boolean> levelStates;

    private CustomizableLogger(Class<?> type,
                               EnumMap<LogLevel, Boolean> levelStates) {
        this.type = type;
        this.levelStates = levelStates;
    }

    public static CustomizableLoggerBuilder builder(Class<?> type) {
        return new CustomizableLoggerBuilder(type);
    }

    public Class<?> getType() {
        return this.type;
    }

    @Override
    public Boolean isEnabled(LogLevel logLevel) {
        return levelStates.get(logLevel);
    }

    @Override
    public void enable(LogLevel logLevel) {
        levelStates.put(logLevel, true);
    }

    @Override
    public void disable(LogLevel logLevel) {
        levelStates.put(logLevel, false);
    }

    public enum LogLevel{
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    public static class CustomizableLoggerBuilder {

        private final EnumMap<LogLevel, Boolean> levelStates = new EnumMap<>(LogLevel.class);
        private final Class<?> type;

        CustomizableLoggerBuilder(Class<?> type) {
            this.type = type;
        }

        public CustomizableLogger build() {
            fillLevelStates();
            return new CustomizableLogger(
                    type,
                    levelStates
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
    }
}
