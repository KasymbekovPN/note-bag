package ru.kpn.logging;

public interface Logger {
    Boolean isEnabledLevel(LogLevel logLevel);
    void enableLevel(LogLevel logLevel);
    void disableLevel(LogLevel logLevel);
}
