package ru.kpn.strategyCalculator;

public interface StrategyCalculator<R, C> {
    R calculate(RawMessage<C> source);
}
