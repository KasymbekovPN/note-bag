package ru.kpn.rawMessage;

public interface RawMessage<T> {
    T getCode();
    RawMessage<T> add(Object o);
    Object[] getArgs();
}
