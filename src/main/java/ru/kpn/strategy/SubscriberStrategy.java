package ru.kpn.strategy;

import java.util.Optional;

// TODO: 04.09.2021 rename to strategy
public interface SubscriberStrategy<T, R> {
    Optional<R> execute(T value);
    default Integer getPriority(){return -1;}
}
