package ru.kpn.logging;

public interface Logger<L> {
    LoggerSetting<L> getSetting();
    void setSetting(LoggerSetting<L> setting);
    void trace(String template, Object... args);
    void debug(String template, Object... args);
    void info(String template, Object... args);
    void warn(String template, Object... args);
    void error(String template, Object... args);
}
