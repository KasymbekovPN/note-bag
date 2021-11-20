package ru.kpn.objectExtraction.builder;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.MatcherDatum;
import ru.kpn.objectExtraction.factory.MatcherFactory;
import ru.kpn.objectExtraction.factory.ObjectFactory;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Function;

public class MatcherBuilder extends BaseBuilder<MatcherDatum, Function<Update, Boolean>, MatcherFactory.Type> {

    private static final EnumMap<MatcherFactory.Type, Function<MatcherDatum, Optional<Object[]>>> CHECK_AND_PREPARE = new EnumMap<>(MatcherFactory.Type.class){{
        put(MatcherFactory.Type.CONSTANT, MatcherBuilder::checkAndPrepareConstant);
        put(MatcherFactory.Type.REGEX, MatcherBuilder::checkAndPrepareRegex);
        put(MatcherFactory.Type.MULTI_REGEX, MatcherBuilder::checkAndPrepareMultiRegex);
    }};

    public MatcherBuilder(ObjectFactory<MatcherFactory.Type, Function<Update, Boolean>> objectFactory, RawMessageFactory<String> messageFactory) {
        super(objectFactory, messageFactory);
    }

    @Override
    public Builder<MatcherDatum, Function<Update, Boolean>> doScenario() {
        checkMatcherDataExistence();
        prepareMatcherType();
        checkAndPrepareArgs();
        return this;
    }

    private void checkMatcherDataExistence() {
        if (result.getSuccess() && datum == null){
            result.makeFailure(messageFactory.create("data.notExist.forSth").add(key));
        }
    }

    private void prepareMatcherType() {
        if (result.getSuccess()){
            String datumType = datum.getType();
            if (datumType != null){
                try{
                    type = MatcherFactory.Type.valueOf(datumType);
                } catch (IllegalArgumentException ex){
                    result.makeFailure(messageFactory.create("type.invalid.where").add(datumType).add(key));
                }
            } else {
                result.makeFailure(messageFactory.create("type.isNull").add(key));
            }
        }
    }

    private void checkAndPrepareArgs() {
        if (result.getSuccess()){
            Optional<Object[]> maybeArgs = CHECK_AND_PREPARE.get(type).apply(datum);
            if (maybeArgs.isPresent()){
                this.args = maybeArgs.get();
            } else {
                result.makeFailure(messageFactory.create("arguments.invalid.forSth").add(key));
            }
        }
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
