package ru.kpn.objectFactory.factory;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.type.DatumType;
import ru.kpn.rawMessage.RawMessage;

import java.util.Map;
import java.util.function.Function;

public class MatcherFactory extends BaseObjectFactory<MatcherDatum, Function<Update, Boolean>> {

    public static Builder builder(){
        return new Builder();
    }

    private MatcherFactory(Map<DatumType, Creator<MatcherDatum, Function<Update, Boolean>, RawMessage<String>>> creators) {
        super(creators);
    }

    @Override
    protected Result<Function<Update, Boolean>, RawMessage<String>> getWrongResult(MatcherDatum datum) {
        Result<Function<Update, Boolean>, RawMessage<String>> result = super.getWrongResult(datum);
        result.getStatus().setCode("matcherFactory.wrongType").add(datum.getType().asStr());
        return result;
    }

    public static class Builder extends BaseBuilder<MatcherDatum, Function<Update, Boolean>>{
        @Override
        protected void check() throws Exception {
            if (MatcherDatumType.ALLOWED_TYPE.values().length != creators.size()){
                throw new Exception("Not completely map of creators");
            }
        }

        @Override
        protected ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessage<String>> create() {
            return new MatcherFactory(creators);
        }
    }
}
