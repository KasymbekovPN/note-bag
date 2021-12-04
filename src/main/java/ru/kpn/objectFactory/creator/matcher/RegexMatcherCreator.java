package ru.kpn.objectFactory.creator.matcher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.RegexMatcher;
import ru.kpn.objectFactory.creator.CreatorWithType;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.OptimisticResult;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

import java.util.function.Function;

@Component
public class RegexMatcherCreator implements CreatorWithType<MatcherDatum, MatcherDatumType, Function<Update, Boolean>, RawMessage<String>> {

    private static final String NAME = "RegexMatcherCreator";
    private static final MatcherDatumType TYPE = new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.REGEX.name());

    @Override
    public Result<Function<Update, Boolean>, RawMessage<String>> create(MatcherDatum datum) {
        return new InnerCreator(new OptimisticResult<>(), datum)
                .checkDatumOnNull()
                .checkTemplateOnNull()
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
                result.setValue(new RegexMatcher(datum.getTemplate()));
            }
            return result;
        }

        public InnerCreator checkDatumOnNull() {
            if (result.getSuccess() && datum == null){
                result.toFailAndGetStatus().setCode("datum.isNull").add(NAME);
            }
            return this;
        }

        public InnerCreator checkTemplateOnNull() {
            if (result.getSuccess() && datum.getTemplate() == null){
                result.toFailAndGetStatus().setCode("datum.template.isNull").add(NAME);
            }
            return this;
        }
    }
}
