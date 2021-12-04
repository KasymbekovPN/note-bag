package ru.kpn.objectFactory.factory;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.type.DatumType;
import ru.kpn.rawMessage.RawMessage;

import java.util.Map;
import java.util.function.Function;

public class ExtractorFactory extends BaseObjectFactory<ExtractorDatum, Function<Update, String>>{

    public static Builder builder(){
        return new Builder();
    }

    private ExtractorFactory(Map<DatumType, Creator<ExtractorDatum, Function<Update, String>, RawMessage<String>>> creators) {
        super(creators);
    }

    @Override
    protected void toFail(RawMessage<String> status, ExtractorDatum datum) {
        status.setCode("strategyInitFactory.wrongType").add(datum.getType().asStr());
    }

    public static class Builder extends BaseBuilder<ExtractorDatum, Function<Update, String>>{
        @Override
        protected void check() throws Exception {
            if (ExtractorDatumType.ALLOWED_TYPE.values().length != creators.size()){
                // TODO: 01.12.2021 other exception !!!
                throw new Exception("Not completely map of creators");
            }
        }

        @Override
        protected ObjectFactory<ExtractorDatum, Function<Update, String>, RawMessage<String>> create() {
            return new ExtractorFactory(creators);
        }
    }
}
