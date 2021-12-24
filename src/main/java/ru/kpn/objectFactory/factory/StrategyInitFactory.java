package ru.kpn.objectFactory.factory;

import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderFactoryOld;

import java.util.Map;

public class StrategyInitFactory extends BaseObjectFactory<StrategyInitDatumType, StrategyInitDatum, Integer> {

    // TODO: 24.12.2021 del
    public static Builder builder(){
        return new Builder();
    }

    public StrategyInitFactory(Map<StrategyInitDatumType, TypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, Seed<String>>> creators) {
        super(creators);
    }

    @Override
    protected Result<Integer, Seed<String>> getWrongResult(StrategyInitDatum datum) {
        Seed<String> seed = StringSeedBuilderFactoryOld.builder().code("strategyInitFactory.wrongType").arg(datum.getType().asStr()).build();
        return new ValuedResult<>(false, seed);
    }

    // TODO: 24.12.2021 del 
    public static class Builder extends AbstractBuilder<StrategyInitDatumType, StrategyInitDatum, Integer, Seed<String>>{
        @Override
        public ResultBuilder<ObjectFactory<StrategyInitDatum, Integer, Seed<String>>, Seed<String>> check() {
            if (success && StrategyInitDatumType.ALLOWED_TYPE.values().length != creators.size()){
                success = false;
                status = StringSeedBuilderFactoryOld.builder().code("notCompletely.creators.strategyInit").build();
            }
            return this;
        }

        @Override
        public ResultBuilder<ObjectFactory<StrategyInitDatum, Integer, Seed<String>>, Seed<String>> calculateValue() {
            if (success){
                value = new StrategyInitFactory(creators);
            }
            return this;
        }

        @Override
        protected Result<ObjectFactory<StrategyInitDatum, Integer, Seed<String>>, Seed<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<ObjectFactory<StrategyInitDatum, Integer, Seed<String>>, Seed<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }
    }
}