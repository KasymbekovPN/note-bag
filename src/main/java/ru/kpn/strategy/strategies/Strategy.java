package ru.kpn.strategy.strategies;

import ru.kpn.rawMessage.RawMessage;

import java.util.Optional;

// TODO: 11.12.2021 move to strategy 
public interface Strategy<T, R> {
    RawMessage<String> runAndGetRawMessage(T value);
    Optional<R> execute(T value);
    default Integer getPriority(){return -1;}
}
