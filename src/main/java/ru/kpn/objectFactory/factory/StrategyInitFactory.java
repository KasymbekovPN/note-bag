package ru.kpn.objectFactory.factory;

import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.type.DatumType;
import ru.kpn.rawMessage.RawMessage;

import java.util.Map;

public class StrategyInitFactory extends BaseObjectFactory<StrategyInitDatum, Integer> {

    public static Builder builder(){
        return new Builder();
    }

    private StrategyInitFactory(Map<DatumType, Creator<StrategyInitDatum, Integer, RawMessage<String>>> creators) {
        super(creators);
    }

    @Override
    protected Result<Integer, RawMessage<String>> getWrongResult(StrategyInitDatum datum) {
        Result<Integer, RawMessage<String>> result = super.getWrongResult(datum);
        result.getStatus().setCode("strategyInitFactory.wrongType").add(datum.getType().asStr());
        return result;
    }

    public static class Builder extends BaseBuilder<StrategyInitDatum, Integer>{
        @Override
        protected void check() throws Exception {
            if (StrategyInitDatumType.ALLOWED_TYPE.values().length != creators.size()){
                // TODO: 29.11.2021 other exception !!!!
                throw new Exception("Not completely map of creators");
            }
        }

        @Override
        protected ObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> create() {
            return new StrategyInitFactory(creators);
        }
    }
}