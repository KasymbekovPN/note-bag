package ru.kpn.strategyCalculator;

public interface StrategyCalculator<R> {

    // TODO: 22.09.2021 impl must be sync
    R calculate(String code, Object... args);
}
