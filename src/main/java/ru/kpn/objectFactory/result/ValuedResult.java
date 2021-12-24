package ru.kpn.objectFactory.result;

import ru.kpn.objectFactory.results.result.SimpleResult;
import ru.kpn.seed.Seed;

public class ValuedResult<V> extends SimpleResult<V, Seed<String>>{
    public ValuedResult(Boolean success, V value, Seed<String> status) {
        super(success, value, status);
    }

    public ValuedResult(Boolean success, Seed<String> status) {
        super(success, status);
    }

    public ValuedResult(V value) {
        super(value);
    }
}
