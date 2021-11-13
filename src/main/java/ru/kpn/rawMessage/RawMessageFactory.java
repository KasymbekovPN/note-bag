package ru.kpn.rawMessage;

public interface RawMessageFactory<T> {
    RawMessage<T> create(T code);
}
