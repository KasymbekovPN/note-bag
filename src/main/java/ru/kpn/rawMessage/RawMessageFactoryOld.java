package ru.kpn.rawMessage;

// TODO: 23.12.2021 del
public interface RawMessageFactoryOld<T> {
    RawMessageOld<T> create(T code);
}
