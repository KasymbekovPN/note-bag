package ru.kpn.objectExtraction.creator.matcher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.MultiRegexMatcher;
import ru.kpn.objectExtraction.creator.CreatorWithType;
import ru.kpn.objectExtraction.datum.MatcherDatum;
import ru.kpn.objectExtraction.result.OptimisticResult;
import ru.kpn.objectExtraction.type.MatcherDatumType;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

import java.util.function.Function;

@Component
public class MultiRegexMatcherCreator implements CreatorWithType<MatcherDatum, MatcherDatumType, Function<Update, Boolean>, RawMessage<String>> {

    private static final String NAME = "MultiRegexMatcherCreator";
    private static final MatcherDatumType TYPE = new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.MULTI_REGEX.name());

    @Override
    public Result<Function<Update, Boolean>, RawMessage<String>> create(MatcherDatum datum) {
        return new InnerCreator(new OptimisticResult<>(), datum)
                .checkDatumOnNull()
                .checkDatumTemplatesIsNull()
                .checkDatumTemplatesIsEmpty()
                .create();
    }

    @Override
    public MatcherDatumType getType() {
        return TYPE;
    }

    @AllArgsConstructor
    private static class InnerCreator{
        private final OptimisticResult<Function<Update, Boolean>> result;
        private final MatcherDatum datum;

        public Result<Function<Update, Boolean>, RawMessage<String>> create() {
            if (result.getSuccess()){
                result.setValue(new MultiRegexMatcher(datum.getTemplates()));
            }
            return result;
        }

        public InnerCreator checkDatumOnNull() {
            if (result.getSuccess() && datum == null){
                result.toFailAndGetStatus().setCode("datum.isNull").add(NAME);
            }
            return this;
        }

        public InnerCreator checkDatumTemplatesIsNull() {
            if (result.getSuccess() && datum.getTemplates() == null){
                result.toFailAndGetStatus().setCode("datum.templates.isNull").add(NAME);
            }
            return this;
        }

        public InnerCreator checkDatumTemplatesIsEmpty() {
            if (result.getSuccess() && datum.getTemplates().isEmpty()){
                result.toFailAndGetStatus().setCode("datum.templates.isEmpty").add(NAME);
            }
            return this;
        }
    }
}
