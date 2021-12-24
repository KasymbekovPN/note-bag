package ru.kpn.objectFactory.factory;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderService;

import java.util.Map;
import java.util.function.Function;

public class MatcherFactory extends BaseObjectFactory<MatcherDatumType, MatcherDatum, Function<Update, Boolean>> {

    private final StringSeedBuilderService seedBuilderService;

    public MatcherFactory(Map<MatcherDatumType, TypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, Seed<String>>> creators,
                          StringSeedBuilderService seedBuilderService) {
        super(creators);
        this.seedBuilderService = seedBuilderService;
    }

    @Override
    protected Result<Function<Update, Boolean>, Seed<String>> getWrongResult(MatcherDatum datum) {
        final Seed<String> seed = seedBuilderService.takeNew().code("matcherFactory.wrongType").arg(datum.getType().asStr()).build();
        return new ValuedResult<>(false, seed);
    }
}
