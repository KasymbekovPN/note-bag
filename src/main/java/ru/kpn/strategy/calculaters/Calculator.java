package ru.kpn.strategy.calculaters;

import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

public interface Calculator<R, T> {
    Result<R, RawMessage<String>> calculate(T value);
}
