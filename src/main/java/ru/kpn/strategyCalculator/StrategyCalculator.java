package ru.kpn.strategyCalculator;

public interface StrategyCalculator<R> {
    R calculate(String code, Object... args);
}
