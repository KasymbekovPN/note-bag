package ru.kpn.tube.strategy;

import java.util.Optional;

public interface SubscriberStrategy<T, R> {
    Optional<R> execute(T value);
}
