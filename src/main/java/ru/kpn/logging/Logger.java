package ru.kpn.logging;

public interface Logger<L> {
    Boolean isEnabled(L logLevel);
    void enable(L logLevel);
    void disable(L logLevel);
    void trace(String template, Object... args);
    void debug(String template, Object... args);
    void info(String template, Object... args);
    void warn(String template, Object... args);
    void error(String template, Object... args);
}
