package ru.kpn.objectExtraction.factory;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.ExtractorDatum;
import ru.kpn.objectExtraction.result.OptimisticResult;
import ru.kpn.objectExtraction.type.ExtractorDatumType;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.factory.AbstractObjectFactory;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ExtractorFactory extends AbstractObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>> {

    private final Map<ExtractorDatumType, Creator<ExtractorDatum, Function<Update, String>, RawMessage<String>>> creators;

    public static Builder builder(){
        return new Builder();
    }

    private ExtractorFactory(Map<ExtractorDatumType, Creator<ExtractorDatum, Function<Update, String>, RawMessage<String>>> creators) {
        this.creators = creators;
    }

    @Override
    protected Result<Function<Update, String>, RawMessage<String>> getResult(ExtractorDatum datum) {
        return creators.get(datum.getType()).create(datum);
    }

    @Override
    protected Result<Function<Update, String>, RawMessage<String>> getWrongResult(ExtractorDatum datum) {
        OptimisticResult<Function<Update, String>> result = new OptimisticResult<>();
        result.setSuccess(false);
        result.toFailAndGetStatus().setCode("strategyInitFactory.wrongType").add(datum.getType().asStr());
        return result;
    }

    public static class Builder {
        private final Map<ExtractorDatumType, Creator<ExtractorDatum, Function<Update, String>, RawMessage<String>>> creators
                = new HashMap<>();

        public Builder creator(ExtractorDatumType type, Creator<ExtractorDatum, Function<Update, String>, RawMessage<String>> creator){
            creators.put(type, creator);
            return this;
        }

        public ExtractorFactory build() throws Exception {
            checkCreators();
            return new ExtractorFactory(creators);
        }

        private void checkCreators() throws Exception {
            if (ExtractorDatumType.ALLOWED_TYPE.values().length != creators.size()){
                // TODO: 01.12.2021 other exception !!!
                throw new Exception("Not completely map of creators");
            }
        }
    }
}
