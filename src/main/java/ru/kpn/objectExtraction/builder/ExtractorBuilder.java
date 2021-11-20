package ru.kpn.objectExtraction.builder;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.objectExtraction.datum.ExtractorDatum;
import ru.kpn.objectExtraction.factory.ExtractorFactory;
import ru.kpn.objectExtraction.factory.ObjectFactory;
import ru.kpn.rawMessage.RawMessageFactory;

import java.util.EnumMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class ExtractorBuilder extends BaseBuilder<ExtractorDatum, Function<Update, String> , ExtractorFactory.Type> {

    private static final EnumMap<ExtractorFactory.Type, Function<ExtractorDatum, Optional<Object[]>>> CHECK_AND_PREPARE = new EnumMap<>(ExtractorFactory.Type.class){{
        put(ExtractorFactory.Type.BY_PREFIX, ExtractorBuilder::checkAndPrepareByPrefixesArgs);
    }};

    public ExtractorBuilder(ObjectFactory<ExtractorFactory.Type, Function<Update, String>> objectFactory, RawMessageFactory<String> messageFactory) {
        super(objectFactory, messageFactory);
    }

    @Override
    public Builder<ExtractorDatum, Function<Update, String>> doScenario() {
        checkDatumExistence();
        prepareType();
        checkAndPrepareArgs();
        return this;
    }

    private void checkDatumExistence() {
        if (result.getSuccess() && datum == null){
            result.makeFailure(messageFactory.create("data.notExist.forSth").add(key));
        }
    }

    private void prepareType() {
        if (result.getSuccess()){
            String datumType = datum.getType();
            if (datumType != null){
                try{
                    this.type = ExtractorFactory.Type.valueOf(datumType);
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
                args = maybeArgs.get();
            } else {
                result.makeFailure(messageFactory.create("arguments.invalid.forSth").add(key));
            }
        }
    }

    private static Optional<Object[]> checkAndPrepareByPrefixesArgs(ExtractorDatum datum){
        Set<String> prefixes = datum.getPrefixes();
        if (prefixes != null && prefixes.size() != 0){
            Object[] objects = prefixes.stream().map((s) -> s.concat(" ")).toArray();
            return Optional.of(objects);
        }
        return Optional.empty();
    }
}
