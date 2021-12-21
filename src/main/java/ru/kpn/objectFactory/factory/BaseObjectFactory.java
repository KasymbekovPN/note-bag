package ru.kpn.objectFactory.factory;

import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.Datum;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.DatumType;
import ru.kpn.rawMessage.RawMessage;

import java.util.Map;

abstract public class BaseObjectFactory<T extends DatumType, D extends Datum<? extends DatumType>, RT> extends AbstractObjectFactory<D, RT, RawMessage<String>> {
    protected final Map<T, TypedCreator<T, D, RT, RawMessage<String>>> creators;

    public BaseObjectFactory(Map<T, TypedCreator<T, D, RT, RawMessage<String>>> creators) {
        this.creators = creators;
    }

    @Override
    protected Result<RT, RawMessage<String>> getResult(D datum) {
        return creators.get(datum.getType()).create(datum);
    }
}
