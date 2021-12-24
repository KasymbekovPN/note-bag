package ru.kpn.objectFactory.factory;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.ExtractorDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.ExtractorDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderFactory;

import java.util.Map;
import java.util.function.Function;

public class ExtractorFactory extends BaseObjectFactory<ExtractorDatumType, ExtractorDatum, Function<Update, String>> {

    public static Builder builder(){
        return new Builder();
    }

    public ExtractorFactory(Map<ExtractorDatumType, TypedCreator<ExtractorDatumType, ExtractorDatum, Function<Update, String>, Seed<String>>> creators) {
        super(creators);
    }

    @Override
    protected Result<Function<Update, String>, Seed<String>> getWrongResult(ExtractorDatum datum) {
        Seed<String> seed = StringSeedBuilderFactory.builder().code("extractorFactory.wrongType").arg(datum.getType().asStr()).build();
        return new ValuedResult<>(false, seed);
    }

    public static class Builder extends AbstractBuilder<ExtractorDatumType, ExtractorDatum, Function<Update, String>, Seed<String>>{
        @Override
        public ResultBuilder<ObjectFactory<ExtractorDatum, Function<Update, String>, Seed<String>>, Seed<String>> check() {
            if (success && ExtractorDatumType.ALLOWED_TYPE.values().length != creators.size()){
                success = false;
                status = StringSeedBuilderFactory.builder().code("notCompletely.creators.extractor").build();
            }
            return this;
        }

        @Override
        public ResultBuilder<ObjectFactory<ExtractorDatum, Function<Update, String>, Seed<String>>, Seed<String>> calculateValue() {
            if (success){
                value = new ExtractorFactory(creators);
            }
            return this;
        }

        @Override
        protected Result<ObjectFactory<ExtractorDatum, Function<Update, String>, Seed<String>>, Seed<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<ObjectFactory<ExtractorDatum, Function<Update, String>, Seed<String>>, Seed<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }
    }
}
