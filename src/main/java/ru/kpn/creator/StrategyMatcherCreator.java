package ru.kpn.creator;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.MatcherFactory;
import ru.kpn.matcher.MatcherType;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

import java.util.*;
import java.util.function.Function;

// TODO: 08.11.2021 rename
@Service
@ConfigurationProperties(prefix = "telegram.tube")
public class StrategyMatcherCreator {

    private final Map<String, Result> results = new HashMap<>();

    private MatcherFactory<Update, Boolean> factory;

    private Map<String, Datum> matcherInitData;

    @Autowired
    public void setFactory(MatcherFactory<Update, Boolean> factory) {
        this.factory = factory;
    }

    public void setMatcherInitData(Map<String, Datum> matcherInitData) {
        this.matcherInitData = matcherInitData;
    }

    public Result getOrCreate(String name){
        if (results.containsKey(name)){
            return results.get(name);
        }

        Result newResult = attemptCreateNewMatcher(name);
        results.put(name, newResult);

        return newResult;
    }

    private Result attemptCreateNewMatcher(String name) {
        Builder builder = new Builder();
        return builder
                .name(name)
                .matcherData(matcherInitData.getOrDefault(name, null))
                .factory(factory)
                .checkMatcherDataExistence()
                .prepareMatcherType()
                .checkAndPrepareArgs()
                .build();
    }

    @Setter
    @Getter
    public static class Datum {
        private String type;
        private Boolean constant;
        private String template;
        private Set<String> templates;
    }

    @lombok.Builder
    @Getter
    public static class Result{
        private Boolean success;
        private Function<Update, Boolean> matcher;
        private RawMessage<String> rawMessage;
    }

    @Getter
    public static class Builder {

        private static final EnumMap<MatcherType, Function<Datum, Optional<Object[]>>> CHECK_AND_PREPARE = new EnumMap<>(MatcherType.class){{
            put(MatcherType.CONSTANT, Builder::checkAndPrepareConstant);
            put(MatcherType.REGEX, Builder::checkAndPrepareRegex);
            put(MatcherType.MULTI_REGEX, Builder::checkAndPrepareMultiRegex);
        }};

        private Datum datum;
        private String name;
        private MatcherFactory<Update, Boolean> factory;

        private Boolean success = true;
        private MatcherType type;
        private Object[] args;
        private RawMessage<String> rawMessage;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder matcherData(Datum datum){
            this.datum = datum;
            return this;
        }

        public Builder factory(MatcherFactory<Update, Boolean> factory){
            this.factory = factory;
            return this;
        }

        public Builder checkMatcherDataExistence(){
            if (success && datum == null){
                success = false;
                rawMessage = new BotRawMessage("data.notExist.forSth").add(name);
            }

            return this;
        }

        public Builder prepareMatcherType(){
            if (success){
                final String datumType = datum.getType();
                if (datumType != null){
                    try{
                        type = MatcherType.valueOf(datumType);
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
            Result.ResultBuilder builder = Result.builder().success(this.success).rawMessage(rawMessage);
            if (success){
                builder.matcher(factory.create(type, args));
            }
            return builder.build();
        }

        private static Optional<Object[]> checkAndPrepareConstant(Datum datum){
            if (datum.getConstant() != null){
                return Optional.of(new Object[]{datum.getConstant()});
            }
            return Optional.empty();
        }

        private static Optional<Object[]> checkAndPrepareRegex(Datum datum){
            if (datum.getTemplate() != null){
                return Optional.of(new Object[]{datum.getTemplate()});
            }
            return Optional.empty();
        }

        private static Optional<Object[]> checkAndPrepareMultiRegex(Datum datum){
            final Set<String> templates = datum.getTemplates();
            if (templates != null && templates.size() != 0){
                return Optional.of(templates.toArray());
            }
            return Optional.empty();
        }
    }
}
