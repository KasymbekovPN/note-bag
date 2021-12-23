package ru.kpn.rawMessage;

public interface RawSource<T> {
    T getCode();
    Object[] getArgs();
}
