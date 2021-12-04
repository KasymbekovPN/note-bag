package ru.kpn.objectExtraction.creator.extractor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ByPrefixExtractor;
import ru.kpn.objectExtraction.creator.CreatorWithType;
import ru.kpn.objectExtraction.datum.ExtractorDatum;
import ru.kpn.objectExtraction.result.OptimisticResult;
import ru.kpn.objectExtraction.type.ExtractorDatumType;
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
        return new InnerCreator(new OptimisticResult<>(), datum)
                .checkDatumOnNull()
                .checkPrefixesAreNull()
                .checkPrefixesAreEmpty()
                .create();
    }

    @Override
    public ExtractorDatumType getType() {
        return TYPE;
    }

    @AllArgsConstructor
    private static class InnerCreator{
        private final OptimisticResult<Function<Update, String>> result;
        private final ExtractorDatum datum;

        public Result<Function<Update, String>, RawMessage<String>> create() {
            if (result.getSuccess()){
                Set<String> prefixes = datum.getPrefixes().stream().map(s -> s + " ").collect(Collectors.toSet());
                result.setValue(new ByPrefixExtractor(prefixes));
            }
            return result;
        }

        public InnerCreator checkDatumOnNull() {
            if (result.getSuccess() && datum == null){
                result.toFailAndGetStatus().setCode("datum.isNull").add(NAME);
            }
            return this;
        }

        public InnerCreator checkPrefixesAreNull() {
            if (result.getSuccess() && datum.getPrefixes() == null){
                result.toFailAndGetStatus().setCode("datum.prefixes.isNull").add(NAME);
            }
            return this;
        }

        public InnerCreator checkPrefixesAreEmpty() {
            if (result.getSuccess() && datum.getPrefixes().isEmpty()){
                result.toFailAndGetStatus().setCode("datum.prefixes.empty").add(NAME);
            }
            return this;
        }
    }
}
