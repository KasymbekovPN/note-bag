package ru.kpn.objectFactory.creator.matcher;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.ConstantMatcher;
import ru.kpn.objectFactory.creator.BaseCreator;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.results.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.SeedBuilderService;

import java.util.function.Function;

@Component
public class ConstantMatcherCreator extends BaseCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>>{

    public ConstantMatcherCreator() {
        super(new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.CONSTANT.name()));
    }

    @Override
    protected AbstractResultBuilder<Function<Update, Boolean>, Seed<String>> createBuilder(MatcherDatum datum) {
        return new Builder(datum, this.getClass().getSimpleName(), seedBuilderService);
    }

    private static class Builder extends BaseBuilder<Function<Update, Boolean>, MatcherDatum>{
        public Builder(MatcherDatum datum, String key, SeedBuilderService<String> seedBuilderService) {
            super(datum, key, seedBuilderService);
        }

        @Override
        protected Function<Update, Boolean> calculateValueImpl() {
            return new ConstantMatcher(datum.getConstant());
        }

        @Override
        public ResultBuilder<Function<Update, Boolean>, Seed<String>> check() {
            checkDatumOnNull();
            checkConstantIsNull();
            return this;
        }

        private void checkDatumOnNull() {
            if (success && datum == null){
                setFailStatus(takeNewSeedBuilder().code("datum.isNull").arg(key).build());
            }
        }

        private void checkConstantIsNull() {
            if (success && datum.getConstant() == null){
                setFailStatus(takeNewSeedBuilder().code("datum.constant.isNull").arg(key).build());
            }
        }
    }
}