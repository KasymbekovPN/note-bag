// TODO: 27.11.2021 ddel
//package ru.kpn.objectExtraction.factory;
//
//import org.telegram.telegrambots.meta.api.objects.Update;
//import ru.kpn.exception.RawMessageException;
//
//import java.util.Map;
//import java.util.function.Function;
//
//public class ExtractorFactory extends BaseFactory<ExtractorFactory.Type, Function<Update, String>>{
//
//    public static Builder builder(){
//        return new Builder();
//    }
//
//    private ExtractorFactory(Map<Type, Function<Object[], Function<Update, String>>> creators) {
//        super(creators);
//    }
//
//    public static class Builder extends BaseFactory.Builder<Type, Function<Update, String>>{
//        @Override
//        protected void checkCreatorsAmount() throws RawMessageException {
//            if (creators.size() != Type.values().length){
//                // TODO: 17.11.2021 add code to file
//                throw new RawMessageException("extractor.notCompletely.building");
//            }
//        }
//
//        @Override
//        protected ObjectFactory<Type, Function<Update, String>> create() {
//            return new ExtractorFactory(creators);
//        }
//    }
//
//    public enum Type{
//        BY_PREFIX
//    }
//}
