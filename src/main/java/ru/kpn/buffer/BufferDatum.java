package ru.kpn.buffer;

public interface BufferDatum<T, C> {
    T getType();
    C getContent();
}
