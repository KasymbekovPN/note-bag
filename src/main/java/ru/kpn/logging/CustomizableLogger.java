package ru.kpn.logging;

import ru.kpn.exception.CustomizableLoggerBuildException;

import java.util.EnumMap;

class CustomizableLogger implements Logger{
    private final Class<?> type;
    private final EnumMap<LogLevel, Boolean> levelStates;

    private CustomizableLogger(Class<?> type,
                               EnumMap<LogLevel, Boolean> levelStates) {
        this.type = type;
        this.levelStates = levelStates;
    }

    public static CustomizableLoggerBuilder builder() {
        return new CustomizableLoggerBuilder();
    }

    public Class<?> getType() {
        return this.type;
    }

    @Override
    public Boolean isEnabledLevel(LogLevel logLevel) {
        return levelStates.get(logLevel);
    }

    @Override
    public void enableLevel(LogLevel logLevel) {
        levelStates.put(logLevel, true);
    }

    @Override
    public void disableLevel(LogLevel logLevel) {
        levelStates.put(logLevel, false);
    }

    public static class CustomizableLoggerBuilder {

        private final EnumMap<LogLevel, Boolean> levelStates = new EnumMap<>(LogLevel.class);

        private Class<?> type;

        CustomizableLoggerBuilder() {
        }

        public CustomizableLoggerBuilder type(Class<?> type) {
            this.type = type;
            return this;
        }

        public CustomizableLoggerBuilder enableLevel(LogLevel logLevel) {
            this.levelStates.put(logLevel, true);
            return this;
        }

        public CustomizableLogger build() throws CustomizableLoggerBuildException {
            fillLevelStates();
            checkType();
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

        private void checkType() throws CustomizableLoggerBuildException {
            if (type == null){
                throw new CustomizableLoggerBuildException("Type is null");
            }
        }
    }
}
