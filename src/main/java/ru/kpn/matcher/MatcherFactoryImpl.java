package ru.kpn.matcher;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumMap;
import java.util.function.Function;

public class MatcherFactoryImpl implements MatcherFactory<Update, Boolean> {

    private final EnumMap<MatcherType, Function<Object[], Function<Update, Boolean>>> creators;

    public static Builder builder(){
        return new Builder();
    }

    private MatcherFactoryImpl(EnumMap<MatcherType, Function<Object[], Function<Update, Boolean>>> creators) {
        this.creators = creators;
    }

    @Override
    public Function<Update, Boolean> create(MatcherType matcherType, Object... args) {
        return creators.get(matcherType).apply(args);
    }

    public static class Builder{
        private final EnumMap<MatcherType, Function<Object[], Function<Update, Boolean>>> creators = new EnumMap<>(MatcherType.class);

        public Builder creator(MatcherType type, Function<Object[], Function<Update, Boolean>> creator){
            creators.put(type, creator);
            return this;
        }

        public MatcherFactoryImpl build() throws Exception {
            checkCreatorsAmount();
            return new MatcherFactoryImpl(creators);
        }

        private void checkCreatorsAmount() throws Exception {
            if (creators.size() != MatcherType.values().length){
                // TODO: 03.11.2021 return exception with source of message
                throw new Exception("Matcher creators aren't set completely");
            }
        }
    }
}
