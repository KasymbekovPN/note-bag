package ru.kpn.creator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ExtractorFactory;
import ru.kpn.extractor.ExtractorType;

import java.util.*;
import java.util.function.Function;

@Service
@ConfigurationProperties(prefix = "telegram.tube")
public class StrategyExtractorCreator {

    private final Map<String, Result> results = new HashMap<>();

    private ExtractorFactory<Update, String> factory;

    private Map<String, Datum> extractorInitData;

    @Autowired
    public void setFactory(ExtractorFactory<Update, String> factory) {
        this.factory = factory;
    }

    public void setExtractorInitData(Map<String, Datum> extractorInitData) {
        this.extractorInitData = extractorInitData;
    }

    public Result getOrCreate(String name){
        if (results.containsKey(name)){
            return results.get(name);
        }

        Result newResult = attemptCreateNewExtractor(name);
        results.put(name, newResult);

        return newResult;
    }

    private Result attemptCreateNewExtractor(String name) {
        return new Builder()
                .name(name)
                .datum(extractorInitData.getOrDefault(name, null))
                .factory(factory)
                .checkDatumExistence()
                .prepareType()
                .checkAndPrepareArgs()
                .build();
    }

    @Setter
    @Getter
    public static class Datum {
        private String type;
        private Set<String> prefixes;
    }

    @lombok.Builder
    @Getter
    public static class Result {
        private Boolean success;
        private Function<Update, String> extractor;
        private String errorMessage;
    }

    public static class Builder {

        private static final EnumMap<ExtractorType, Function<Datum, Optional<Object[]>>> CHECK_AND_PREPARE = new EnumMap<>(ExtractorType.class){{
            put(ExtractorType.BY_PREFIX, Builder::checkAndPrepareByPrefixesArgs);
        }};

        private Datum datum;
        private String name;
        private ExtractorFactory<Update, String> factory;

        private Boolean success = true;
        private ExtractorType type;
        private Object[] args;
        // TODO: 06.11.2021  StrategyCalculatorSource!!!
        private String errorMessage = "";

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder datum(Datum datum){
            this.datum = datum;
            return this;
        }

        public Builder factory(ExtractorFactory<Update, String> factory){
            this.factory = factory;
            return this;
        }

        public Builder checkDatumExistence(){
            if (success && datum == null){
                success = false;
                errorMessage = String.format("Extractor data for '%s' doesn't exist", name);
            }
            return this;
        }

        public Builder prepareType(){
            if (success){
                final String datumType = datum.getType();
                if (datumType != null){
                    try{
                        this.type = ExtractorType.valueOf(datumType);
                    } catch (IllegalArgumentException ex){
                        success = false;
                        errorMessage = String.format("Type '%s' is invalid [%s]", datum.getType(), name);
                    }
                } else {
                    success = false;
                    errorMessage = String.format("Type for '%s' is null", name);
                }
            }
            return this;
        }

        public Builder checkAndPrepareArgs(){
            if (success){
                Optional<Object[]> maybeArgs = CHECK_AND_PREPARE.get(type).apply(datum);
                if (maybeArgs.isPresent()){
                    args = maybeArgs.get();
                } else {
                    success = false;
                    errorMessage = String.format("Invalid args for [%s]", name);
                }
            }
            return this;
        }

        public Result build(){
            Result.ResultBuilder builder = Result.builder().success(success).errorMessage(errorMessage);
            if (success){
                builder.extractor(factory.create(type, args));
            }
            return builder.build();
        }

        private static Optional<Object[]> checkAndPrepareByPrefixesArgs(Datum datum){
            Set<String> prefixes = datum.getPrefixes();
            if (prefixes != null && prefixes.size() != 0){
                Object[] objects = prefixes.stream().map((s) -> s.concat(" ")).toArray();
                return Optional.of(objects);
            }
            return Optional.empty();
        }
    }
}
