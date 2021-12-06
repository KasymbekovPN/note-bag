package ru.kpn.objectFactory.creator.extractor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;
import ru.kpn.objectFactory.creator.CreatorWithType;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.result.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.result.builder.ResultBuilder;
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ByPrefixesExtractorCreator implements CreatorWithType<ExtractorDatum, ExtractorDatumType, Function<Update, String>, RawMessage<String>> {

    private static final String NAME = "ByPrefixesExtractorCreator";
    private static final ExtractorDatumType TYPE = new ExtractorDatumType(ExtractorDatumType.ALLOWED_TYPE.BY_PREFIX.name());

    @Override
    public synchronized Result<Function<Update, String>, RawMessage<String>> create(ExtractorDatum datum) {
        return new ResultBuilderImpl(datum)
                .checkDatumOnNull()
                .checkPrefixesAreNull()
                .checkPrefixesAreEmpty()
                .calculateValue()
                .build();
    }

    @Override
    public ExtractorDatumType getType() {
        return TYPE;
    }

    @AllArgsConstructor
    private static class ResultBuilderImpl extends AbstractResultBuilder<Function<Update, String>>{
        private final ExtractorDatum datum;

        public ResultBuilderImpl checkDatumOnNull(){
            if (success && datum == null){
                success = false;
                status.setCode("datum.isNull").add(NAME);
            }
            return this;
        }

        private ResultBuilderImpl checkPrefixesAreNull(){
            if (success && datum.getPrefixes() == null){
                success = false;
                status.setCode("datum.prefixes.isNull").add(NAME);
            }
            return this;
        }

        private ResultBuilderImpl checkPrefixesAreEmpty(){
            if (success && datum.getPrefixes().isEmpty()){
                success = false;
                status.setCode("datum.prefixes.empty").add(NAME);
            }
            return this;
        }

        @Override
        public ResultBuilder<Function<Update, String>> calculateValue() {
            if (success){
                Set<String> prefixes = datum.getPrefixes().stream().map(s -> s + " ").collect(Collectors.toSet());
                value = new ByPrefixExtractor(prefixes);
            }
            return this;
        }
    }
}
