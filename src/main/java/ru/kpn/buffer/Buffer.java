package ru.kpn.buffer;

import java.util.Optional;

public interface Buffer<K, V> {
    int add(K key, V datum);
    int getSize(K key);
    Optional<V> peek(K key);
    Optional<V> poll(K key);
    void clear(K key);
    V createDatum(Object... args);
}
