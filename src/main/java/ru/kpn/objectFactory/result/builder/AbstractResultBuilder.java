package ru.kpn.objectFactory.result.builder;

import ru.kpn.objectFactory.result.Result;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

abstract public class AbstractResultBuilder<V> implements ResultBuilder<V> {
    protected boolean success = true;
    protected V value;
    protected RawMessage<String> status = new BotRawMessage();

    @Override
    public Result<V, RawMessage<String>> build() {
        return success ? new ValuedResult<>(value) : new ValuedResult<>(status);
    }
}
