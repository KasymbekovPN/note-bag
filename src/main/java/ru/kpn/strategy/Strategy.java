package ru.kpn.strategy;

import ru.kpn.strategyCalculator.StrategyCalculatorSource;

import java.util.Optional;

public interface Strategy<T, R> {

    // TODO: 16.10.2021 not default
    default StrategyCalculatorSource<String> runAndGetAnswer(T value){ return null;}

    default Integer getPriority(){return -1;}
    Optional<R> execute(T value);
}
