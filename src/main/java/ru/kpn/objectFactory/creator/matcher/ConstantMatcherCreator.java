package ru.kpn.objectFactory.creator.matcher;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.ConstantMatcher;
import ru.kpn.objectFactory.creator.CreatorWithType;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.OptimisticResult;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

import java.util.function.Function;

@Component
public class ConstantMatcherCreator implements CreatorWithType<MatcherDatum, MatcherDatumType, Function<Update, Boolean>, RawMessage<String>> {

    private static final String NAME = "ConstantMatcherCreator";
    private static final MatcherDatumType TYPE = new MatcherDatumType(MatcherDatumType.ALLOWED_TYPE.CONSTANT.name());

    @Override
    public Result<Function<Update, Boolean>, RawMessage<String>> create(MatcherDatum datum) {
        return new InnerCreator(new OptimisticResult<>(), datum)
                .checkDatumOnNull()
                .checkConstantIsNull()
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
                result.setValue(new ConstantMatcher(datum.getConstant()));
            }
            return result;
        }

        public InnerCreator checkDatumOnNull() {
            if (result.getSuccess() && datum == null){
                result.toFailAndGetStatus().setCode("datum.isNull").add(NAME);
            }
            return this;
        }

        public InnerCreator checkConstantIsNull() {
            if (result.getSuccess() && datum.getConstant() == null){
                result.toFailAndGetStatus().setCode("datum.constant.isNull").add(NAME);
            }
            return this;
        }
    }
}
