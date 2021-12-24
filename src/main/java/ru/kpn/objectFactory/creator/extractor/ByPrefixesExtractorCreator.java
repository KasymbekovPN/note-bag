package ru.kpn.objectFactory.creator.extractor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;
import ru.kpn.objectFactory.creator.BaseCreator;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.results.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.SeedBuilderService;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ByPrefixesExtractorCreator extends BaseCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>> {

    public ByPrefixesExtractorCreator() {
        super(new ExtractorDatumType(ExtractorDatumType.ALLOWED_TYPE.BY_PREFIX.name()));
    }

    @Override
    protected AbstractResultBuilder<Function<Update, String>, Seed<String>> createBuilder(ExtractorDatum datum) {
        return new Builder(datum, this.getClass().getSimpleName(), seedBuilderService);
    }

    private static class Builder extends BaseBuilder<Function<Update, String>, ExtractorDatum>{

        public Builder(ExtractorDatum datum, String key, SeedBuilderService<String> seedBuilderService) {
            super(datum, key, seedBuilderService);
        }

        @Override
        protected Function<Update, String> calculateValueImpl() {
            Set<String> prefixes = datum.getPrefixes().stream().map(s -> s + " ").collect(Collectors.toSet());
            return new ByPrefixExtractor(prefixes);
        }

        @Override
        public ResultBuilder<Function<Update, String>, Seed<String>> check() {
            checkDatumOnNull();
            checkPrefixesAreNull();
            checkPrefixesAreEmpty();
            return this;
        }

        private void checkDatumOnNull() {
            if (success && datum == null){
                setFailStatus(takeNewSeedBuilder().code("datum.isNull").arg(key).build());
            }
        }

        private void checkPrefixesAreNull() {
            if (success && datum.getPrefixes() == null){
                setFailStatus(takeNewSeedBuilder().code("datum.prefixes.isNull").arg(key).build());
            }
        }

        private void checkPrefixesAreEmpty() {
            if (success && datum.getPrefixes().isEmpty()){
                setFailStatus(takeNewSeedBuilder().code("datum.prefixes.empty").arg(key).build());
            }
        }
    }
}