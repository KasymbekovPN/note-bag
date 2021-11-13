package ru.kpn.strategyCalculator;

import ru.kpn.rawMessage.RawMessage;

public interface StrategyCalculator<R, C> {
    R calculate(RawMessage<C> source);
}
