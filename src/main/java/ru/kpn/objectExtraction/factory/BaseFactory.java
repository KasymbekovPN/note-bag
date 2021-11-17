package ru.kpn.objectExtraction.factory;

import ru.kpn.exception.RawMessageException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

abstract public class BaseFactory<T, R> implements ObjectFactory<T, R> {

    protected final Map<T, Function<Object[], R>> creators;

    protected BaseFactory(Map<T, Function<Object[], R>> creators) {
        this.creators = creators;
    }

    @Override
    public R create(T type, Object... args) {
        return creators.get(type).apply(args);
    }

    abstract public static class Builder<T, R>{
        protected final Map<T, Function<Object[], R>> creators = new HashMap<>();

        public Builder<T, R> creator(T type, Function<Object[], R> creator){
            creators.put(type, creator);
            return this;
        }

        public ObjectFactory<T, R> build() throws RawMessageException {
            checkCreatorsAmount();
            return create();
        }

        protected abstract void checkCreatorsAmount() throws RawMessageException;
        protected abstract ObjectFactory<T,R> create();
    }
}
