package ru.kpn.rawMessage;

public interface RawMessage<T> {
    T getCode();
    RawMessage<T> setCode(T code);
    RawMessage<T> add(Object o);
    Object[] getArgs();
}
