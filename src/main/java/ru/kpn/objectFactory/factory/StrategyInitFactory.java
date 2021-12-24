package ru.kpn.objectFactory.factory;

import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.SeedBuilderService;

import java.util.Map;

public class StrategyInitFactory extends BaseObjectFactory<StrategyInitDatumType, StrategyInitDatum, Integer> {

    private final SeedBuilderService<String> seedBuilderService;

    public StrategyInitFactory(Map<StrategyInitDatumType, TypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, Seed<String>>> creators,
                               SeedBuilderService<String> seedBuilderService) {
        super(creators);
        this.seedBuilderService = seedBuilderService;
    }

    @Override
    protected Result<Integer, Seed<String>> getWrongResult(StrategyInitDatum datum) {
        Seed<String> seed = seedBuilderService.takeNew().code("strategyInitFactory.wrongType").arg(datum.getType().asStr()).build();
        return new ValuedResult<>(false, seed);
    }
}