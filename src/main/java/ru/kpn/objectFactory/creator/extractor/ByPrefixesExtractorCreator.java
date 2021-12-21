package ru.kpn.objectFactory.creator.extractor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;
import ru.kpn.objectFactory.creator.AbstractTypedCreator;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ByPrefixesExtractorCreator extends AbstractTypedCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>, RawMessage<String>> {

    private static final String NAME = "ByPrefixesExtractorCreator";
    private static final ExtractorDatumType TYPE = new ExtractorDatumType(ExtractorDatumType.ALLOWED_TYPE.BY_PREFIX.name());

    @Override
    public ExtractorDatumType getType() {
        return TYPE;
    }

    @Override
    protected AbstractResultBuilder<Function<Update, String>, RawMessage<String>> createBuilder(ExtractorDatum datum) {
        return new Builder(datum);
    }

    @AllArgsConstructor
    private static class Builder extends AbstractResultBuilder<Function<Update, String>, RawMessage<String>>{
        private final ExtractorDatum datum;

        @Override
        public ResultBuilder<Function<Update, String>, RawMessage<String>> check() {
            checkDatumOnNull();
            checkPrefixesAreNull();
            checkPrefixesAreEmpty();
            return this;
        }

        @Override
        public ResultBuilder<Function<Update, String>, RawMessage<String>> calculateValue() {
            if (success){
                Set<String> prefixes = datum.getPrefixes().stream().map(s -> s + " ").collect(Collectors.toSet());
                value = new ByPrefixExtractor(prefixes);
            }
            return this;
        }

        @Override
        protected Result<Function<Update, String>, RawMessage<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<Function<Update, String>, RawMessage<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }

        private void checkDatumOnNull() {
            if (success && datum == null){
                success = false;
                status = new BotRawMessage("datum.isNull").add(NAME);
            }
        }

        private void checkPrefixesAreNull() {
            if (success && datum.getPrefixes() == null){
                success = false;
                status = new BotRawMessage("datum.prefixes.isNull").add(NAME);
            }
        }

        private void checkPrefixesAreEmpty() {
            if (success && datum.getPrefixes().isEmpty()){
                success = false;
                status = new BotRawMessage("datum.prefixes.empty").add(NAME);
            }
        }
    }
}