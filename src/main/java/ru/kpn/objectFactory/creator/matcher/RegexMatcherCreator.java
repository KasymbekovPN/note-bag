package ru.kpn.objectFactory.creator.matcher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.RegexMatcher;
import ru.kpn.objectFactory.creator.CreatorWithType;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.result.builder.ResultBuilder;
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
        return new ResultBuilderImpl(datum)
                .checkDatumOnNull()
                .checkTemplateOnNull()
                .calculateValue()
                .build();
    }

    @Override
    public MatcherDatumType getType() {
        return TYPE;
    }

    @AllArgsConstructor
    private static class ResultBuilderImpl extends AbstractResultBuilder<Function<Update, Boolean>> {
        private final MatcherDatum datum;

        public ResultBuilderImpl checkDatumOnNull(){
            if (success && datum == null){
                success = false;
                status.setCode("datum.isNull").add(NAME);
            }
            return this;
        }

        public ResultBuilderImpl checkTemplateOnNull() {
            if (success && datum.getTemplate() == null){
                success = false;
                status.setCode("datum.template.isNull").add(NAME);
            }
            return this;
        }

        @Override
        public ResultBuilder<Function<Update, Boolean>> calculateValue() {
            if (success){
                value = new RegexMatcher(datum.getTemplate());
            }
            return this;
        }
    }
}
