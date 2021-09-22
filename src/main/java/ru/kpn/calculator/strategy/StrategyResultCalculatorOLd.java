package ru.kpn.calculator.strategy;

// TODO: 22.09.2021 del
public interface StrategyResultCalculatorOLd<T, I> {
    T calculate(I id, Object content);
}
