package ru.kpn.objectExtraction.factory;

import ru.kpn.objectExtraction.datum.StrategyInitDatum;
import ru.kpn.objectExtraction.result.OptimisticResult;
import ru.kpn.objectExtraction.type.StrategyInitDatumType;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.factory.AbstractObjectFactory;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

import java.util.HashMap;
import java.util.Map;

// TODO: 01.12.2021 need base class
public class StrategyInitFactory extends AbstractObjectFactory<StrategyInitDatum, Integer, RawMessage<String>> {

    private final Map<StrategyInitDatumType, Creator<StrategyInitDatum, Integer, RawMessage<String>>> creators;

    public static Builder builder() {
        return new Builder();
    }

    private StrategyInitFactory(Map<StrategyInitDatumType, Creator<StrategyInitDatum, Integer, RawMessage<String>>> creators) {
        this.creators = creators;
    }

    @Override
    protected Result<Integer, RawMessage<String>> getResult(StrategyInitDatum datum) {
        return creators.get(datum.getType()).create(datum);
    }

    @Override
    protected Result<Integer, RawMessage<String>> getWrongResult(StrategyInitDatum datum) {
        OptimisticResult<Integer> result = new OptimisticResult<>();
        result.setSuccess(false);
        result.toFailAndGetStatus().setCode("strategyInitFactory.wrongType").add(datum.getType().asStr());
        return result;
    }

    public static class Builder {
        private final Map<StrategyInitDatumType, Creator<StrategyInitDatum, Integer, RawMessage<String>>> creators = new HashMap<>();

        public Builder creator(StrategyInitDatumType type, Creator<StrategyInitDatum, Integer, RawMessage<String>> creator){
            creators.put(type, creator);
            return this;
        }

        public StrategyInitFactory build() throws Exception {
            checkCreators();
            return new StrategyInitFactory(creators);
        }

        private void checkCreators() throws Exception {
            if (StrategyInitDatumType.ALLOWED_TYPE.values().length != creators.size()){
                // TODO: 29.11.2021 other exception !!!!
                throw new Exception("Not completely map of creators");
            }
        }
    }
}
