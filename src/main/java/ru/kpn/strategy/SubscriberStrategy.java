package ru.kpn.strategy;

import java.util.Optional;

public interface SubscriberStrategy<T, R> {
    Optional<R> execute(T value);
    default Integer getPriority(){return -1;}
}
