package ru.kpn.strategyCalculator;

import ru.kpn.rawMessage.RawMessage;

// TODO: 11.12.2021 move to strategy
// TODO: 11.12.2021 rename 
public interface StrategyCalculator<R, C> {
    R calculate(RawMessage<C> source);
}
