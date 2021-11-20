package ru.kpn.objectExtraction.builder;

import ru.kpn.objectExtraction.result.Result;

public interface Builder<D, RT> {
    Builder<D,RT> start(String key);
    Builder<D,RT> datum(D datum);
    Builder<D,RT> doScenario();
    Result<RT> build();
}
