package ru.kpn.creator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.ExtractorFactory;
import ru.kpn.extractor.ExtractorType;
import ru.kpn.strategyCalculator.BotRawMessage;
import ru.kpn.strategyCalculator.RawMessage;

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
        private RawMessage<String> rawMessage;
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
        private RawMessage<String> rawMessage;

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
                rawMessage = new BotRawMessage("data.notExist.forSth").add(name);
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
                        rawMessage = new BotRawMessage("type.invalid.where").add(datumType).add(name);
                    }
                } else {
                    success = false;
                    rawMessage = new BotRawMessage("type.isNull").add(name);
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
                    rawMessage = new BotRawMessage("arguments.isInvalid.forSth").add(name);
                }
            }
            return this;
        }

        public Result build(){
            Result.ResultBuilder builder = Result.builder().success(success).rawMessage(rawMessage);
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
