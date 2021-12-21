package ru.kpn.objectFactory.result;

import ru.kpn.objectFactory.results.result.SimpleResult;
import ru.kpn.rawMessage.RawMessage;

public class ValuedResult<V> extends SimpleResult<V, RawMessage<String>>{
    public ValuedResult(Boolean success, V value, RawMessage<String> status) {
        super(success, value, status);
    }

    public ValuedResult(Boolean success, RawMessage<String> status) {
        super(success, status);
    }

    public ValuedResult(V value) {
        super(value);
    }
}
