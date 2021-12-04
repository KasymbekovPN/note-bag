//package ru.kpn.objectExtraction.factory;
//
//import org.telegram.telegrambots.meta.api.objects.Update;
//import ru.kpn.exception.RawMessageException;
//
//import java.util.Map;
//import java.util.function.Function;
//
//public class MatcherFactory extends BaseFactory<MatcherFactory.Type, Function<Update, Boolean>>{
//
//    public static Builder builder(){
//        return new Builder();
//    }
//
//    private MatcherFactory(Map<Type, Function<Object[], Function<Update, Boolean>>> creators) {
//        super(creators);
//    }
//
//    public static class Builder extends BaseFactory.Builder<Type, Function<Update, Boolean>>{
//        @Override
//        protected void checkCreatorsAmount() throws RawMessageException {
//            if (creators.size() != Type.values().length){
//                // TODO: 17.11.2021 add code to file 
//                throw new RawMessageException("matcher.notCompletely.building");
//            }
//        }
//
//        @Override
//        protected ObjectFactory<Type, Function<Update, Boolean>> create() {
//            return new MatcherFactory(creators);
//        }
//    }
//
//    public enum Type{
//        CONSTANT,
//        REGEX,
//        MULTI_REGEX
//    }
//}
