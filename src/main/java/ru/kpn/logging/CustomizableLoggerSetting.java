package ru.kpn.logging;

import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class CustomizableLoggerSetting implements LoggerSetting<CustomizableLogger.LogLevel> {

    private final Map<CustomizableLogger.LogLevel, Boolean> logStatus;

    private CustomizableLoggerSetting(Map<CustomizableLogger.LogLevel, Boolean> logStatus) {
        this.logStatus = logStatus;
    }

    public static CustomizableLoggerSettingBuilder builder() {
        return new CustomizableLoggerSettingBuilder();
    }

    @Override
    public Boolean get(CustomizableLogger.LogLevel logLevel) {
        return logStatus.getOrDefault(logLevel, false);
    }

    public static class CustomizableLoggerSettingBuilder {
        private final Map<CustomizableLogger.LogLevel, Boolean> logStatus = new HashMap<>();

        CustomizableLoggerSettingBuilder() {
        }

        public CustomizableLoggerSetting build() {
            return new CustomizableLoggerSetting(logStatus);
        }

        public CustomizableLoggerSettingBuilder enable(CustomizableLogger.LogLevel logLevel){
            logStatus.put(logLevel, true);
            return this;
        }
    }
}
