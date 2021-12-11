package ru.kpn.strategy.injectors;

import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

public interface Injector<D, RT> {
    Result<RT, RawMessage<String>> inject(Object object, String name);
}
