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
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.util.function.Function;

@Component
public class MultiRegexMatcherCreator extends AbstractTypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, RawMessage<String>> {

    private static final String NAME = "MultiRegexMatcherCreator";
    private static final MatcherDatumType TYPE = new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.MULTI_REGEX.name());

    @Override
    public MatcherDatumType getType() {
        return TYPE;
    }

    @Override
    protected AbstractResultBuilder<Function<Update, Boolean>, RawMessage<String>> createBuilder(MatcherDatum datum) {
        return new Builder(datum);
    }

    @AllArgsConstructor
    private static class Builder extends AbstractResultBuilder<Function<Update, Boolean>, RawMessage<String>>{
        private final MatcherDatum datum;

        @Override
        public ResultBuilder<Function<Update, Boolean>, RawMessage<String>> check() {
            checkDatumOnNull();
            checkDatumTemplatesIsNull();
            checkDatumTemplatesIsEmpty();
            return this;
        }

        @Override
        public ResultBuilder<Function<Update, Boolean>, RawMessage<String>> calculateValue() {
            if (success){
                value = new MultiRegexMatcher(datum.getTemplates());
            }
            return this;
        }

        @Override
        protected Result<Function<Update, Boolean>, RawMessage<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<Function<Update, Boolean>, RawMessage<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }

        private void checkDatumOnNull() {
            if (success && datum == null){
                success = false;
                status = new BotRawMessage("datum.isNull").add(NAME);
            }
        }

        private void checkDatumTemplatesIsNull() {
            if (success && datum.getTemplates() == null){
                success = false;
                status = new BotRawMessage("datum.templates.isNull").add(NAME);
            }
        }

        private void checkDatumTemplatesIsEmpty() {
            if (success && datum.getTemplates().isEmpty()){
                success = false;
                status = new BotRawMessage("datum.templates.isEmpty").add(NAME);
            }
        }
    }
}
