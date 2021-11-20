package ru.kpn.objectExtraction.builder;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.MatcherDatum;
import ru.kpn.objectExtraction.factory.MatcherFactory;
import ru.kpn.objectExtraction.factory.ObjectFactory;
import ru.kpn.objectExtraction.result.Result;
import ru.kpn.objectExtraction.result.ResultImpl;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Function;

// TODO: 18.11.2021 need base class
@RequiredArgsConstructor
public class MatcherBuilder implements Builder<MatcherDatum, Function<Update, Boolean>> {

    private static final EnumMap<MatcherFactory.Type, Function<MatcherDatum, Optional<Object[]>>> CHECK_AND_PREPARE = new EnumMap<>(MatcherFactory.Type.class){{
        put(MatcherFactory.Type.CONSTANT, MatcherBuilder::checkAndPrepareConstant);
        put(MatcherFactory.Type.REGEX, MatcherBuilder::checkAndPrepareRegex);
        put(MatcherFactory.Type.MULTI_REGEX, MatcherBuilder::checkAndPrepareMultiRegex);
    }};

    private final ObjectFactory<MatcherFactory.Type, Function<Update, Boolean>> factory;
    private final RawMessageFactory<String> rawMessageFactory;

    private Result<Function<Update, Boolean>> result;
    private String key;
    private MatcherDatum datum;
    private MatcherFactory.Type type;
    private Object[] args;

    @Override
    public Builder<MatcherDatum, Function<Update, Boolean>> start(String key) {
        this.result = new ResultImpl<>();
        this.key = key;
        return this;
    }

    @Override
    public Builder<MatcherDatum, Function<Update, Boolean>> datum(MatcherDatum datum) {
        this.datum = datum;
        return this;
    }

    @Override
    public Builder<MatcherDatum, Function<Update, Boolean>> doScenario() {
        checkMatcherDataExistence();
        prepareMatcherType();
        checkAndPrepareArgs();
        return this;
    }

    @Override
    public Result<Function<Update, Boolean>> build() {
        Result<Function<Update, Boolean>> result = create();
        reset();
        return result;
    }

    private void checkMatcherDataExistence() {
        if (result.getSuccess() && datum == null){
            result.makeFailure(rawMessageFactory.create("data.notExist.forSth").add(key));
        }
    }

    private void prepareMatcherType() {
        if (result.getSuccess()){
            String datumType = datum.getType();
            if (datumType != null){
                try{
                    type = MatcherFactory.Type.valueOf(datumType);
                } catch (IllegalArgumentException ex){
                    result.makeFailure(rawMessageFactory.create("type.invalid.where").add(datumType).add(key));
                }
            } else {
                result.makeFailure(rawMessageFactory.create("type.isNull").add(key));
            }
        }
    }

    private void checkAndPrepareArgs() {
        if (result.getSuccess()){
            Optional<Object[]> maybeArgs = CHECK_AND_PREPARE.get(type).apply(datum);
            if (maybeArgs.isPresent()){
                this.args = maybeArgs.get();
            } else {
                result.makeFailure(rawMessageFactory.create("arguments.invalid.forSth").add(key));
            }
        }
    }

    private void reset() {
        key = null;
        datum = null;
        type = null;
        args = null;
    }

    private Result<Function<Update, Boolean>> create() {
        if (result.getSuccess()){
            result.setValue(factory.create(type, args));
        }
        return result;
    }

    private static Optional<Object[]> checkAndPrepareConstant(MatcherDatum datum){
        return datum.getConstant() != null ? Optional.of(new Object[]{datum.getConstant()}) : Optional.empty();
    }

    private static Optional<Object[]> checkAndPrepareRegex(MatcherDatum datum){
        return datum.getTemplate() != null ? Optional.of(new Object[]{datum.getTemplate()}) : Optional.empty();
    }

    private static Optional<Object[]> checkAndPrepareMultiRegex(MatcherDatum datum) {
        return datum.getTemplates() != null ? Optional.of(datum.getTemplates().toArray()) : Optional.empty();
    }
}
