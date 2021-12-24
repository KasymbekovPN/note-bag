package ru.kpn.objectFactory.creator.matcher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.ConstantMatcher;
import ru.kpn.objectFactory.creator.AbstractTypedCreator;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderFactory;

import java.util.function.Function;

@Component
public class ConstantMatcherCreator extends AbstractTypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, Seed<String>> {

    private static final String NAME = "ConstantMatcherCreator";
    private static final MatcherDatumType TYPE = new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.CONSTANT.name());

    @Override
    public MatcherDatumType getType() {
        return TYPE;
    }

    @Override
    protected AbstractResultBuilder<Function<Update, Boolean>, Seed<String>> createBuilder(MatcherDatum datum) {
        return new Builder(datum);
    }

    @AllArgsConstructor
    private static class Builder extends AbstractResultBuilder<Function<Update, Boolean>, Seed<String>> {
        private final MatcherDatum datum;

        @Override
        public ResultBuilder<Function<Update, Boolean>, Seed<String>> check() {
            checkDatumOnNull();
            checkConstantIsNull();
            return this;
        }

        @Override
        public ResultBuilder<Function<Update, Boolean>, Seed<String>> calculateValue() {
            if (success){
                value = new ConstantMatcher(datum.getConstant());
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
                status = StringSeedBuilderFactory.builder().code("datum.isNull").arg(NAME).build();
            }
        }

        private void checkConstantIsNull() {
            if (success && datum.getConstant() == null){
                success = false;
                status = StringSeedBuilderFactory.builder().code("datum.constant.isNull").arg(NAME).build();
            }
        }
    }
}
