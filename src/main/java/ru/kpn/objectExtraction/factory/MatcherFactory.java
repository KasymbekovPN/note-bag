package ru.kpn.objectExtraction.factory;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.MatcherDatum;
import ru.kpn.objectExtraction.type.MatcherDatumType;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.factory.ObjectFactory;
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
    protected void toFail(RawMessage<String> status, MatcherDatum datum) {
        status.setCode("matcherFactory.wrongType").add(datum.getType().asStr());
    }

    public static class Builder extends BaseBuilder<MatcherDatum, Function<Update, Boolean>>{
        @Override
        protected void check() throws Exception {
            if (MatcherDatumType.ALLOWED_TYPE.values().length != creators.size()){
                // TODO: 01.12.2021 other exception !!!
                throw new Exception("Not completely map of creators");
            }
        }

        @Override
        protected ObjectFactory<MatcherDatum, Function<Update, Boolean>, RawMessage<String>> create() {
            return new MatcherFactory(creators);
        }
    }
}
