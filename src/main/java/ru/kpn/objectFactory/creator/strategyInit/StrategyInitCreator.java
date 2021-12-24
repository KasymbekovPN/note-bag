package ru.kpn.objectFactory.creator.strategyInit;

import org.springframework.stereotype.Component;
import ru.kpn.objectFactory.creator.BaseCreator;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.results.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.SeedBuilderService;

@Component
public class StrategyInitCreator extends BaseCreator<StrategyInitDatumType, StrategyInitDatum, Integer> {

    public StrategyInitCreator() {
        super(new StrategyInitDatumType(StrategyInitDatumType.ALLOWED_TYPE.COMMON.name()));
    }

    @Override
    protected AbstractResultBuilder<Integer, Seed<String>> createBuilder(StrategyInitDatum datum) {
        return new Builder(datum, getClass().getSimpleName(), seedBuilderService);
    }

    private static class Builder extends BaseBuilder<Integer, StrategyInitDatum>{
        public Builder(StrategyInitDatum datum, String key, SeedBuilderService<String> seedBuilderService) {
            super(datum, key, seedBuilderService);
        }

        @Override
        protected Integer calculateValueImpl() {
            return datum.getPriority();
        }

        @Override
        public ResultBuilder<Integer, Seed<String>> check() {
            checkDatumOnNull();
            checkPriorityOnNull();
            return this;
        }

        private void checkDatumOnNull() {
            if (success && datum == null){
                setFailStatus(takeNewSeedBuilder().code("datum.isNull").arg(key).build());
            }
        }

        private void checkPriorityOnNull() {
            if (success && datum.getPriority() == null){
                setFailStatus(takeNewSeedBuilder().code("datum.priority.isNull").arg(key).build());
            }
        }
    }
}