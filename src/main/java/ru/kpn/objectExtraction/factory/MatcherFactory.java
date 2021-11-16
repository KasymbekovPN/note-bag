package ru.kpn.objectExtraction.factory;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumMap;
import java.util.function.Function;

// TODO: 16.11.2021 insert according datum as inner static class
// TODO: 16.11.2021 some functional into base class
public class MatcherFactory implements ObjectFactory<MatcherFactory.Type, Function<Update, Boolean>>{

    private final EnumMap<Type, Function<Object[], Function<Update, Boolean>>> creators;

    public MatcherFactory(EnumMap<Type, Function<Object[], Function<Update, Boolean>>> creators) {
        this.creators = creators;
    }

    public static Builder build(){
        return new Builder();
    }

    @Override
    public Function<Update, Boolean> create(Type type, Object... args) {
        return creators.get(type).apply(args);
    }

    public static class Builder{

        private final EnumMap<Type, Function<Object[], Function<Update, Boolean>>> creators = new EnumMap<>(Type.class);

        public Builder creator(Type type, Function<Object[], Function<Update, Boolean>> creator){
            creators.put(type, creator);
            return this;
        }

        public MatcherFactory build() throws MatcherFactoryBuildException {
            checkCreatorsAmount();
            return new MatcherFactory(creators);
        }

        private void checkCreatorsAmount() throws MatcherFactoryBuildException {
            if (creators.size() != Type.values().length){
                throw new MatcherFactoryBuildException("matcher.notCompletely.building");
            }
        }
    }

    public enum Type{
        CONSTANT,
        REGEX,
        MULTI_REGEX
    }
}
