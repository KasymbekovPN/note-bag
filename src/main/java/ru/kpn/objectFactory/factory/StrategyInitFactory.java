package ru.kpn.objectFactory.factory;

import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.rawMessage.BotRawMessageOld;
import ru.kpn.rawMessage.RawMessageOld;

import java.util.Map;

public class StrategyInitFactory extends BaseObjectFactory<StrategyInitDatumType, StrategyInitDatum, Integer> {

    public static Builder builder(){
        return new Builder();
    }

    public StrategyInitFactory(Map<StrategyInitDatumType, TypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, RawMessageOld<String>>> creators) {
        super(creators);
    }

    @Override
    protected Result<Integer, RawMessageOld<String>> getWrongResult(StrategyInitDatum datum) {
        return new ValuedResult<>(false, new BotRawMessageOld("strategyInitFactory.wrongType").add(datum.getType().asStr()));
    }

    public static class Builder extends AbstractBuilder<StrategyInitDatumType, StrategyInitDatum, Integer, RawMessageOld<String>>{
        @Override
        public ResultBuilder<ObjectFactory<StrategyInitDatum, Integer, RawMessageOld<String>>, RawMessageOld<String>> check() {
            if (success && StrategyInitDatumType.ALLOWED_TYPE.values().length != creators.size()){
                success = false;
                status = new BotRawMessageOld("notCompletely.creators.strategyInit");
            }
            return this;
        }

        @Override
        public ResultBuilder<ObjectFactory<StrategyInitDatum, Integer, RawMessageOld<String>>, RawMessageOld<String>> calculateValue() {
            if (success){
                value = new StrategyInitFactory(creators);
            }
            return this;
        }

        @Override
        protected Result<ObjectFactory<StrategyInitDatum, Integer, RawMessageOld<String>>, RawMessageOld<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<ObjectFactory<StrategyInitDatum, Integer, RawMessageOld<String>>, RawMessageOld<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }
    }
}