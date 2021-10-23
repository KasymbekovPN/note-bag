package ru.kpn.strategy;

import ru.kpn.strategyCalculator.StrategyCalculatorSource;

import java.util.Optional;

public interface Strategy<T, R> {
    StrategyCalculatorSource<String> runAndGetAnswer(T value);
    default Integer getPriority(){return -1;}
    Optional<R> execute(T value);
}
