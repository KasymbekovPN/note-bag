// TODO: 27.11.2021 del
//package ru.kpn.objectExtraction.factory;
//
//import ru.kpn.exception.RawMessageException;
//
//import java.util.Map;
//import java.util.function.Function;
//
//// TODO: 20.11.2021 must contain results
//public class StrategyInitFactory extends BaseFactory<StrategyInitFactory.Type, Integer> {
//
//    public static Builder builder(){
//        return new Builder();
//    }
//
//    private StrategyInitFactory(Map<Type, Function<Object[], Integer>> creators) {
//        super(creators);
//    }
//
//    public static class Builder extends BaseFactory.Builder<Type, Integer>{
//        @Override
//        protected void checkCreatorsAmount() throws RawMessageException {}
//
//        @Override
//        protected ObjectFactory<Type, Integer> create() {
//            creators.clear();
//            creators.put(Type.COMMON, new Creator());
//            return new StrategyInitFactory(creators);
//        }
//    }
//
//    public enum Type{
//        COMMON
//    }
//
//    private static class Creator implements Function<Object[], Integer>{
//        @Override
//        public Integer apply(Object[] objects) {
//            return (Integer) objects[0];
//        }
//    }
//}
