package ru.kpn.objectFactory.result.builder;

import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

// TODO: 06.12.2021 move to objectFactory lib
public interface ResultBuilder<V> {
    Result<V, RawMessage<String>> build();
    ResultBuilder<V> calculateValue();
}
