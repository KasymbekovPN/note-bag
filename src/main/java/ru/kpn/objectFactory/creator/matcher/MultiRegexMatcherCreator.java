package ru.kpn.objectFactory.creator.matcher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.MultiRegexMatcher;
import ru.kpn.objectFactory.creator.AbstractTypedCreator;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderFactoryOld;

import java.util.function.Function;

@Component
public class MultiRegexMatcherCreator extends AbstractTypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, Seed<String>> {

    private static final String NAME = "MultiRegexMatcherCreator";
    private static final MatcherDatumType TYPE = new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.MULTI_REGEX.name());

    @Override
    public MatcherDatumType getType() {
        return TYPE;
    }

    @Override
    protected AbstractResultBuilder<Function<Update, Boolean>, Seed<String>> createBuilder(MatcherDatum datum) {
        return new Builder(datum);
    }

    @AllArgsConstructor
    private static class Builder extends AbstractResultBuilder<Function<Update, Boolean>, Seed<String>>{
        private final MatcherDatum datum;

        @Override
        public ResultBuilder<Function<Update, Boolean>, Seed<String>> check() {
            checkDatumOnNull();
            checkDatumTemplatesIsNull();
            checkDatumTemplatesIsEmpty();
            return this;
        }

        @Override
        public ResultBuilder<Function<Update, Boolean>, Seed<String>> calculateValue() {
            if (success){
                value = new MultiRegexMatcher(datum.getTemplates());
            }
            return this;
        }

        @Override
        protected Result<Function<Update, Boolean>, Seed<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<Function<Update, Boolean>, Seed<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }

        private void checkDatumOnNull() {
            if (success && datum == null){
                success = false;
                status = StringSeedBuilderFactoryOld.builder().code("datum.isNull").arg(NAME).build();
            }
        }

        private void checkDatumTemplatesIsNull() {
            if (success && datum.getTemplates() == null){
                success = false;
                status = StringSeedBuilderFactoryOld.builder().code("datum.templates.isNull").arg(NAME).build();
            }
        }

        private void checkDatumTemplatesIsEmpty() {
            if (success && datum.getTemplates().isEmpty()){
                success = false;
                status = StringSeedBuilderFactoryOld.builder().code("datum.templates.isEmpty").arg(NAME).build();
            }
        }
    }
}
