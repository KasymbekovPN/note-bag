package ru.kpn.strategy;

import java.util.Optional;

// TODO: 04.09.2021 rename to strategy
public interface Strategy<T, R> {
    default Integer getPriority(){return -1;}
    Optional<R> execute(T value);
}
