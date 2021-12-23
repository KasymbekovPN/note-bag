package ru.kpn.rawMessage;

public interface RawMessageOld<T> {
    T getCode();
    RawMessageOld<T> setCode(T code);
    RawMessageOld<T> add(Object o);
    Object[] getArgs();
}
