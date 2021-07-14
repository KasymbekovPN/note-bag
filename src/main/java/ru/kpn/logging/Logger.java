package ru.kpn.logging;

public interface Logger<L> {
    Boolean isEnabled(L logLevel);
    void enable(L logLevel);
    void disable(L logLevel);
}
