package ru.kpn.objectFactory.result.builder;

import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

public interface ResultBuilder<V> {
    Result<V, RawMessage<String>> build();
    ResultBuilder<V> calculateValue();
}
