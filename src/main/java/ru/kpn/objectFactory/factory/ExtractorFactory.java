package ru.kpn.objectFactory.factory;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.util.Map;
import java.util.function.Function;

public class ExtractorFactory extends BaseObjectFactory<ExtractorDatumType, ExtractorDatum, Function<Update, String>> {

    public static Builder builder(){
        return new Builder();
    }

    public ExtractorFactory(Map<ExtractorDatumType, TypedCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>, RawMessage<String>>> creators) {
        super(creators);
    }

    @Override
    protected Result<Function<Update, String>, RawMessage<String>> getWrongResult(ExtractorDatum datum) {
        return new ValuedResult<>(false, new BotRawMessage("extractorFactory.wrongType").add(datum.getType().asStr()));
    }

    public static class Builder extends AbstractBuilder<ExtractorDatumType, ExtractorDatum, Function<Update, String>, RawMessage<String>>{
        @Override
        public ResultBuilder<ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>>, RawMessage<String>> check() {
            if (success && ExtractorDatumType.ALLOWED_TYPE.values().length != creators.size()){
                success = false;
                status = new BotRawMessage("notCompletely.creators.extractor");
            }
            return this;
        }

        @Override
        public ResultBuilder<ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>>, RawMessage<String>> calculateValue() {
            if (success){
                value = new ExtractorFactory(creators);
            }
            return this;
        }

        @Override
        protected Result<ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>>, RawMessage<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>>, RawMessage<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }
    }
}
