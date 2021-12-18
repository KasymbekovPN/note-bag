package ru.kpn.strategy.strategies;

import ru.kpn.rawMessage.RawMessage;

import java.util.Optional;

public interface Strategy<T, R> {
    RawMessage<String> runAndGetRawMessage(T value);
    Optional<R> execute(T value);
    default Integer getPriority(){return -1;}
}
