package ru.kpn.calculator.strategy;

public interface StrategyResultCalculator<T, I> {
    T calculate(I id, Object content);
}
