package ru.kpn.objectFactory.factory;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.rawMessage.BotRawMessageOld;
import ru.kpn.rawMessage.RawMessageOld;

import java.util.Map;
import java.util.function.Function;

public class MatcherFactory extends BaseObjectFactory<MatcherDatumType, MatcherDatum, Function<Update, Boolean>> {

    public static Builder builder(){
        return new Builder();
    }

    public MatcherFactory(Map<MatcherDatumType, TypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, RawMessageOld<String>>> creators) {
        super(creators);
    }

    @Override
    protected Result<Function<Update, Boolean>, RawMessageOld<String>> getWrongResult(MatcherDatum datum) {
        return new ValuedResult<>(false, new BotRawMessageOld("matcherFactory.wrongType").add(datum.getType().asStr()));
    }

    public static class Builder extends AbstractBuilder<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, RawMessageOld<String>>{
        @Override
        public ResultBuilder<ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessageOld<String>>, RawMessageOld<String>> check() {
            if (success && MatcherDatumType.ALLOWED_TYPE.values().length != creators.size()){
                success = false;
                status = new BotRawMessageOld("notCompletely.creators.matcher");
            }
            return this;
        }

        @Override
        public ResultBuilder<ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessageOld<String>>, RawMessageOld<String>> calculateValue() {
            if (success){
                value = new MatcherFactory(creators);
            }
            return this;
        }

        @Override
        protected Result<ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessageOld<String>>, RawMessageOld<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessageOld<String>>, RawMessageOld<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }
    }
}
