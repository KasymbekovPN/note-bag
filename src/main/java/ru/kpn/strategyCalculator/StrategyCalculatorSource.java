package ru.kpn.strategyCalculator;

public interface StrategyCalculatorSource<T> {
    T getCode();
    void add(Object o);
    Object[] getArgs();
}
