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
import ru.kpn.statusSeed.BotRawMessageOld;
import ru.kpn.statusSeed.RawMessageOld;

import java.util.function.Function;

@Component
public class ConstantMatcherCreator extends AbstractTypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, RawMessageOld<String>> {

    private static final String NAME = "ConstantMatcherCreator";
    private static final MatcherDatumType TYPE = new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.CONSTANT.name());

    @Override
    public MatcherDatumType getType() {
        return TYPE;
    }

    @Override
    protected AbstractResultBuilder<Function<Update, Boolean>, RawMessageOld<String>> createBuilder(MatcherDatum datum) {
        return new Builder(datum);
    }

    @AllArgsConstructor
    private static class Builder extends AbstractResultBuilder<Function<Update, Boolean>, RawMessageOld<String>> {
        private final MatcherDatum datum;

        @Override
        public ResultBuilder<Function<Update, Boolean>, RawMessageOld<String>> check() {
            checkDatumOnNull();
            checkConstantIsNull();
            return this;
        }

        @Override
        public ResultBuilder<Function<Update, Boolean>, RawMessageOld<String>> calculateValue() {
            if (success){
                value = new ConstantMatcher(datum.getConstant());
            }
            return this;
        }

        @Override
        protected Result<Function<Update, Boolean>, RawMessageOld<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<Function<Update, Boolean>, RawMessageOld<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }

        private void checkDatumOnNull() {
            if (success && datum == null){
                success = false;
                status = new BotRawMessageOld("datum.isNull").add(NAME);
            }
        }

        private void checkConstantIsNull() {
            if (success && datum.getConstant() == null){
                success = false;
                status = new BotRawMessageOld("datum.constant.isNull").add(NAME);
            }
        }
    }
}
