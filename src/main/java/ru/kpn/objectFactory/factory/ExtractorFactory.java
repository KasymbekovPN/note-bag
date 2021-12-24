package ru.kpn.objectFactory.factory;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderService;

import java.util.Map;
import java.util.function.Function;

public class ExtractorFactory extends BaseObjectFactory<ExtractorDatumType, ExtractorDatum, Function<Update, String>> {

    private final StringSeedBuilderService seedBuilderService;

    public ExtractorFactory(Map<ExtractorDatumType, TypedCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>, Seed<String>>> creators,
                            StringSeedBuilderService seedBuilderService) {
        super(creators);
        this.seedBuilderService = seedBuilderService;
    }

    @Override
    protected Result<Function<Update, String>, Seed<String>> getWrongResult(ExtractorDatum datum) {
        Seed<String> seed = seedBuilderService.takeNew().code("extractorFactory.wrongType").arg(datum.getType().asStr()).build();
        return new ValuedResult<>(false, seed);
    }
}
