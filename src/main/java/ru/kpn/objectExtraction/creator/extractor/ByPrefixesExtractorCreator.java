// TODO: 27.11.2021 del
//package ru.kpn.objectExtraction.creator.extractor;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import ru.kpn.extractor.ByPrefixExtractor;
//import ru.kpn.objectExtraction.creator.Creator;
//import ru.kpn.objectExtraction.datum.ExtractorDatum;
//import ru.kpn.objectExtraction.result.Result;
//import ru.kpn.objectExtraction.result.ResultImpl;
//
//import java.util.Set;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Component
//public class ByPrefixesExtractorCreator implements Creator<ExtractorDatum, Function<Update, String>> {
//
//    private static final String NAME = "ByPrefixesExtractorCreator";
//
//    @Override
//    public Result<Function<Update, String>> create(ExtractorDatum datum) {
//        return new Creator(new ResultImpl<>(), datum)
//                .checkDatumOnNull()
//                .checkPrefixesAreNull()
//                .checkPrefixesAreEmpty()
//                .create();
//    }
//
//    @AllArgsConstructor
//    private static class Creator{
//        private final Result<Function<Update, String>> result;
//        private final ExtractorDatum datum;
//
//        public Creator checkDatumOnNull() {
//            if (result.getSuccess() && datum == null){
//                result.takeMessage("datum.isNull").add(NAME);
//            }
//            return this;
//        }
//
//        public Creator checkPrefixesAreNull() {
//            if (result.getSuccess() && datum.getPrefixes() == null){
//                result.takeMessage("datum.prefixes.isNull").add(NAME);
//            }
//            return this;
//        }
//
//        public Creator checkPrefixesAreEmpty() {
//            if (result.getSuccess() && datum.getPrefixes().size() == 0){
//                result.takeMessage("datum.prefixes.empty").add(NAME);
//            }
//            return this;
//        }
//
//        public Result<Function<Update, String>> create(){
//            if (result.getSuccess()){
//                Set<String> prefixes = datum.getPrefixes().stream().map(s -> s + " ").collect(Collectors.toSet());
//                result.setValue(new ByPrefixExtractor(prefixes));
//            }
//            return result;
//        }
//    }
//}
