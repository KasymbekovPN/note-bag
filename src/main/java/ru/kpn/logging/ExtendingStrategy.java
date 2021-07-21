package ru.kpn.logging;

public interface ExtendingStrategy<T> {
    T execute(T value, Object... extensions);
}
