package ru.kpn.objectExtraction.factory;

// TODO: 13.11.2021 rename
public interface ObjectFactory<T, R> {
    R create(T type, Object... args);
}
