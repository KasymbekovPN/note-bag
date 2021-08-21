package ru.kpn.tube.calculator;


import java.util.Optional;

public interface SubscriberCalculator<T, R> {
    Optional<R> calculate(T message);
}
