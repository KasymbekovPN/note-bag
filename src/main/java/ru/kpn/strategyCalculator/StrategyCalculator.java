package ru.kpn.strategyCalculator;

public interface StrategyCalculator<R, C> {
    R calculate(StrategyCalculatorSource<C> source);
}
