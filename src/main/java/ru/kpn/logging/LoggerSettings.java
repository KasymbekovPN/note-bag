package ru.kpn.logging;

public interface LoggerSettings<T> {
    Boolean get(T logLevel);
}
