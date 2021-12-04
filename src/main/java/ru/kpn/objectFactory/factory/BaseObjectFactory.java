package ru.kpn.objectFactory.factory;

import ru.kpn.objectFactory.result.OptimisticResult;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.datum.Datum;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.objectFactory.type.DatumType;
import ru.kpn.rawMessage.RawMessage;

import java.util.HashMap;
import java.util.Map;

abstract public class BaseObjectFactory<D extends Datum<? extends DatumType>, RT> extends AbstractObjectFactory<D, RT, RawMessage<String>> {
    protected final Map<DatumType, Creator<D, RT, RawMessage<String>>> creators;

    protected BaseObjectFactory(Map<DatumType, Creator<D, RT, RawMessage<String>>> creators) {
        this.creators = creators;
    }

    @Override
    protected Result<RT, RawMessage<String>> getResult(D datum) {
        return creators.get(datum.getType()).create(datum);
    }

    @Override
    protected Result<RT, RawMessage<String>> getWrongResult(D datum) {
        OptimisticResult<RT> result = new OptimisticResult<>();
        result.setSuccess(false);
        toFail(result.toFailAndGetStatus(), datum);
        return result;
    }

    protected abstract void toFail(RawMessage<String> status, D datum);

    protected static abstract class BaseBuilder<D extends Datum<? extends DatumType>, RT> {
        protected final Map<DatumType, Creator<D, RT, RawMessage<String>>> creators = new HashMap<>();

        // TODO: 04.12.2021 take type from creator - only one arg
        public BaseBuilder<D, RT> creator(DatumType type, Creator<D, RT, RawMessage<String>> creator){
            creators.put(type, creator);
            return this;
        }

        public ObjectFactory<D, RT, RawMessage<String>> build() throws Exception {
            check();
            return create();
        }

        protected abstract void check() throws Exception;
        protected abstract ObjectFactory<D,RT, RawMessage<String>> create();
    }
}
