package ru.kpn.rawMessage;

// TODO: 27.11.2021 refactoring
public interface RawMessage<T> {
    T getCode();
    RawMessage<T> setCode(T code);
    RawMessage<T> add(Object o);
    Object[] getArgs();
}
