package ru.kpn.logging;

public interface LoggerSetting<T> {
    Boolean get(T logLevel);
}
