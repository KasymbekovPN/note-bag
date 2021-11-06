package ru.kpn.strategyCalculator;

// TODO: 10.10.2021 rename, may be MessageSource
public interface RawMessage<T> {
    T getCode();
    RawMessage<T> add(Object o);
    Object[] getArgs();
}
