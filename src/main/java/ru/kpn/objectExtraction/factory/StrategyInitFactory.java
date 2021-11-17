package ru.kpn.objectExtraction.factory;

import lombok.Getter;
import lombok.Setter;
import ru.kpn.exception.RawMessageException;

import java.util.Map;
import java.util.function.Function;

public class StrategyInitFactory extends BaseFactory<StrategyInitFactory.Type, Integer> {

    public static Builder builder(){
        return new Builder();
    }

    private StrategyInitFactory(Map<Type, Function<Object[], Integer>> creators) {
        super(creators);
    }

    public static class Builder extends BaseFactory.Builder<Type, Integer>{
        @Override
        protected void checkCreatorsAmount() throws RawMessageException {}

        @Override
        protected ObjectFactory<Type, Integer> create() {
            creators.clear();
            creators.put(Type.COMMON, new Creator());
            return new StrategyInitFactory(creators);
        }
    }

    public enum Type{
        COMMON
    }

    @Getter
    @Setter
    public static class Datum{
        private Integer priority;
    }

    private static class Creator implements Function<Object[], Integer>{
        @Override
        public Integer apply(Object[] objects) {
            return (Integer) objects[0];
        }
    }
}
