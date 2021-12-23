package ru.kpn.objectFactory.result;

import ru.kpn.objectFactory.results.result.SimpleResult;
import ru.kpn.rawMessage.RawMessageOld;

public class ValuedResult<V> extends SimpleResult<V, RawMessageOld<String>>{
    public ValuedResult(Boolean success, V value, RawMessageOld<String> status) {
        super(success, value, status);
    }

    public ValuedResult(Boolean success, RawMessageOld<String> status) {
        super(success, status);
    }

    public ValuedResult(V value) {
        super(value);
    }
}
