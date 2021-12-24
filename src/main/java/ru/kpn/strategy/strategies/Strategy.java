package ru.kpn.strategy.strategies;

import ru.kpn.seed.Seed;

import java.util.Optional;

public interface Strategy<T, R> {
    Seed<String> runAndGetRawMessage(T value);
    Optional<R> execute(T value);
    default Integer getPriority(){return -1;}
}
