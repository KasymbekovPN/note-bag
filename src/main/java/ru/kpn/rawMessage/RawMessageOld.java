package ru.kpn.rawMessage;

// TODO: 23.12.2021 del
public interface RawMessageOld<T> {
    T getCode();
    RawMessageOld<T> setCode(T code);
    RawMessageOld<T> add(Object o);
    Object[] getArgs();
}
