package ru.kpn.tube.calculator;

import java.util.Optional;

public interface ExtractorCalculator<T, R> {
    Optional<R> calculate(T message);
}
