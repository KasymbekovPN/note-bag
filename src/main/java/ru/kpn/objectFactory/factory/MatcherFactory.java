package ru.kpn.objectFactory.factory;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectFactory.creator.TypedCreator;
import ru.kpn.objectFactory.datum.MatcherDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.MatcherDatumType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderFactoryOld;

import java.util.Map;
import java.util.function.Function;

public class MatcherFactory extends BaseObjectFactory<MatcherDatumType, MatcherDatum, Function<Update, Boolean>> {

    public static Builder builder(){
        return new Builder();
    }

    public MatcherFactory(Map<MatcherDatumType, TypedCreator<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, Seed<String>>> creators) {
        super(creators);
    }

    @Override
    protected Result<Function<Update, Boolean>, Seed<String>> getWrongResult(MatcherDatum datum) {
        Seed<String> seed = StringSeedBuilderFactoryOld.builder().code("matcherFactory.wrongType").arg(datum.getType().asStr()).build();
        return new ValuedResult<>(false, seed);
    }

    // TODO: 24.12.2021 del
    public static class Builder extends AbstractBuilder<MatcherDatumType, MatcherDatum, Function<Update, Boolean>, Seed<String>>{
        @Override
        public ResultBuilder<ObjectFactory<MatcherDatum, Function<Update, Boolean>, Seed<String>>, Seed<String>> check() {
            if (success && MatcherDatumType.ALLOWED_TYPE.values().length != creators.size()){
                success = false;
                status = StringSeedBuilderFactoryOld.builder().code("notCompletely.creators.matcher").build();
            }
            return this;
        }

        @Override
        public ResultBuilder<ObjectFactory<MatcherDatum, Function<Update, Boolean>, Seed<String>>, Seed<String>> calculateValue() {
            if (success){
                value = new MatcherFactory(creators);
            }
            return this;
        }

        @Override
        protected Result<ObjectFactory<MatcherDatum, Function<Update, Boolean>, Seed<String>>, Seed<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<ObjectFactory<MatcherDatum, Function<Update, Boolean>, Seed<String>>, Seed<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }
    }
}
