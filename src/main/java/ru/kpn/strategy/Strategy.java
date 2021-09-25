package ru.kpn.strategy;

import java.util.Optional;

public interface Strategy<T, R> {
    default Integer getPriority(){return -1;}
    Optional<R> execute(T value);
}
