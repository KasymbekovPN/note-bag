package ru.kpn.objectExtraction.factory;

public interface ObjectFactory<T, R> {
    R create(T type, Object... args);
}
