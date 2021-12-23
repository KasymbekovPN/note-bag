package ru.kpn.rawMessage;

public interface RawMessageFactoryOld<T> {
    RawMessageOld<T> create(T code);
}
