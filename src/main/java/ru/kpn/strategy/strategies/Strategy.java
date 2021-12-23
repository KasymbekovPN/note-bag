package ru.kpn.strategy.strategies;

import ru.kpn.statusSeed.RawMessageOld;

import java.util.Optional;

public interface Strategy<T, R> {
    RawMessageOld<String> runAndGetRawMessage(T value);
    Optional<R> execute(T value);
    default Integer getPriority(){return -1;}
}
