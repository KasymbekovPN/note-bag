package ru.kpn.strategyCalculator;

// TODO: 10.10.2021 rename
public interface StrategyCalculatorSource<T> {
    T getCode();
    void add(Object o);
    Object[] getArgs();
}
